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

    public OS getById(int id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado essa ordem de serviço"));
    }

    @Transactional
    public OS create(CreateOSRequestDTO ordemdeservico, List<MultipartFile> imagensEntrada){
        OS os = mapper.map(ordemdeservico);
        if (!imagensEntrada.isEmpty()){
            os.setImagemEntrada(imageService.uploadImage(os.getId(), imagensEntrada, ImageService.ENTRANCE_METHOD));
        }

        repository.save(os);
        log.info("Ordem de serviço registrada no nome de: " + os.getFuncionario().getNome());
        return os;
    }

    @Transactional
    public void delete(int id){
        repository.deleteById(id);
        log.info("OS {} apagada com sucesso", id);
    }

    @Transactional
    public OS update(int id,
                       UpdateOSRequestDTO os,
                       List<MultipartFile> imagensEntrada,
                       List<MultipartFile> imagensSaida
    ){
        OS ordemCorrespondente = repository.findById(Integer.valueOf(id)).orElseThrow(() -> new NotFoundException("OS não encontrada"));
        OS ordemAtualizada = mapper.updateMap(ordemCorrespondente, os);

        if (!imagensEntrada.isEmpty()){
            ordemAtualizada.setImagemEntrada(imageService.uploadImage(ordemAtualizada.getId(), imagensEntrada, ImageService.ENTRANCE_METHOD));
        }
        if (!imagensSaida.isEmpty()){
            ordemAtualizada.setImagemSaida(imageService.uploadImage(ordemAtualizada.getId(), imagensSaida, ImageService.EXIT_METHOD));
        }

        repository.save(ordemAtualizada);
        log.info("OS atualizada");
        return ordemAtualizada;
    }
}
