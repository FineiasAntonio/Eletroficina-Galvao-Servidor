package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.OS.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Service.OSService;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<OS> create(@RequestBody CreateOSRequestDTO ordemdeservico){
        OS ordemCriada = service.create(ordemdeservico);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemCriada);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<OS> update(@PathVariable int id, @RequestPart UpdateOSRequestDTO os){
        OS ordemAtualizada = service.update(id, os);
        return ResponseEntity.status(HttpStatus.CREATED).body(ordemAtualizada);
    }

    @PostMapping (value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> storageImage(
            @PathVariable int id,
            @RequestParam(name = "method", required = true) int method,
            @RequestBody List<MultipartFile> imagens
    ){
        service.storageImage(id, imagens, method);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
