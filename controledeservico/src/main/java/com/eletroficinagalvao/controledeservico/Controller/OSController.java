package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Service.ImageService;
import com.eletroficinagalvao.controledeservico.Service.OSService;

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
    @Qualifier("OSService")
    private OSService service;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity(service.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity getById(@PathVariable int id){
        return new ResponseEntity(service.getById(String.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity create(
            @RequestPart CreateOSRequestDTO ordemdeservico,
            @RequestPart List<MultipartFile> imagensEntrada
    ){
        service.create(ordemdeservico, imagensEntrada);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        service.delete(String.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity update(@PathVariable int id,
                                 @RequestPart UpdateOSRequestDTO os,
                                 @RequestPart List<MultipartFile> imagensEntrada,
                                 @RequestPart List<MultipartFile> imagensSaida
    ){
        service.update(id, os, imagensEntrada, imagensSaida);
        return new ResponseEntity(HttpStatus.OK);
    }
}
