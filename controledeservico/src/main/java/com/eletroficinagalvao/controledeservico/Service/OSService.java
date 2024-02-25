package com.eletroficinagalvao.controledeservico.Service;

import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Domain.Mapper.OSMapper;
import com.eletroficinagalvao.controledeservico.Exception.NotFoundException;
import com.eletroficinagalvao.controledeservico.Repository.OSRepository;

import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@Service
public class OSService {

    @Autowired
    private OSRepository repository;
    @Autowired
    private OSMapper mapper;
    @Autowired
    private ImageService imageService;

    public List<OS> getAll(){
        return repository.findAll();
    }

    public OS getById(String id){
        return repository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException("Não foi encontrado essa ordem de serviço"));
    }

    @Transactional
    public void create(CreateOSRequestDTO ordemdeservico, List<MultipartFile> imagensEntrada){
        OS os = mapper.map(ordemdeservico);
        if (!imagensEntrada.isEmpty()){
            os.setImagemEntrada(imageService.uploadImage(os.getId(), imagensEntrada, ImageService.ENTRANCE_METHOD));
        }

        repository.insert(os);

        log.info("Ordem de serviço registrada no nome de: " + os.getFuncionario().getNome());
    }

    @Transactional
    public void delete(String id){
        repository.deleteById(Integer.valueOf(id));
        log.info("OS apagada com sucesso");
    }

    @Transactional
    public void update(int id,
                       UpdateOSRequestDTO os,
                       List<MultipartFile> imagensEntrada,
                       List<MultipartFile> imagensSaida
    ){
        OS correspondente = repository.findById(Integer.valueOf(id)).orElseThrow(() -> new NotFoundException("OS não encontrada"));
        OS updatedOS = mapper.updateMap(correspondente, os);

        if (!imagensEntrada.isEmpty()){
            updatedOS.setImagemEntrada(imageService.uploadImage(updatedOS.getId(), imagensEntrada, ImageService.ENTRANCE_METHOD));
        }
        if (!imagensSaida.isEmpty()){
            updatedOS.setImagemSaida(imageService.uploadImage(updatedOS.getId(), imagensSaida, ImageService.EXIT_METHOD));
        }

        repository.save(updatedOS);
        log.info("OS atualizada");
    }
}
