package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Service.ImageService;
import com.eletroficinagalvao.controledeservico.Service.OSService;

import com.google.api.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping ("/ordensdeservicos")
public class OSController {

    @Autowired
    private OSService service;

    @GetMapping
    public ResponseEntity<List<OS>> getAll(){
        List<OS> ordens = service.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(ordens);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<OS> getById(@PathVariable int id){
        OS ordem = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ordem);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OS> create(
            @RequestPart CreateOSRequestDTO ordemdeservico,
            @RequestPart List<MultipartFile> imagensEntrada
    ){
        OS ordemCriada = service.create(ordemdeservico, imagensEntrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemCriada);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OS> update(@PathVariable int id,
                                 @RequestPart UpdateOSRequestDTO os,
                                 @RequestPart List<MultipartFile> imagensEntrada,
                                 @RequestPart List<MultipartFile> imagensSaida
    ){
        OS ordemAtualizada = service.update(id, os, imagensEntrada, imagensSaida);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemAtualizada);
    }
}
