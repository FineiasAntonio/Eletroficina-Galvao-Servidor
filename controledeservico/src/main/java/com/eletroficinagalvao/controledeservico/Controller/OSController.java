package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.CreateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.DTO.UpdateOSRequestDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.OS;
import com.eletroficinagalvao.controledeservico.Service.ImageService;
import com.eletroficinagalvao.controledeservico.Service.NotificationService;
import com.eletroficinagalvao.controledeservico.Service.OSService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.SQLDataException;
import java.util.List;

@RestController
@RequestMapping ("/ordensdeservicos")
public class OSController {

    @Autowired
    @Qualifier("OSService")
    private OSService service;

    @Autowired
    private NotificationService notificationService;

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

    @PostMapping
    public ResponseEntity create(@RequestBody CreateOSRequestDTO ordemdeserviço){
        service.create(ordemdeserviço);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity delete(@PathVariable int id){
        service.delete(String.valueOf(id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody UpdateOSRequestDTO os){
        System.out.println(os);
        service.update(id, os);
        return new ResponseEntity(HttpStatus.OK);
    }

// Fotos
//    public ResponseEntity updateEntranceImage(@PathVariable int id, @RequestParam("image") List<MultipartFile> fotos) {
//        String diretorio = null;
//
//        if (!fotos.isEmpty()) {
//            for (MultipartFile e : fotos) {
//                try {
//                    InputStream inputStream = e.getInputStream();
//                    diretorio = imageService.readImage(id, inputStream, ImageService.ENTRANCE_METHOD);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                } catch (SQLDataException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//
//        OS entity = service.getById(String.valueOf(id));
//        entity.setImagemEntrada(diretorio);
//        service.update(entity);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    public ResponseEntity updateExitImage(@PathVariable int id, @RequestParam("image") List<MultipartFile> fotos) {
//        String diretorio = null;
//
//        if (!fotos.isEmpty()) {
//            for (MultipartFile e : fotos) {
//                try {
//                    InputStream inputStream = e.getInputStream();
//                    diretorio = imageService.readImage(id, inputStream, ImageService.EXIT_METHOD);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                } catch (SQLDataException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
//
//        OS entity = service.getById(String.valueOf(id));
//        entity.setImagemEntrada(diretorio);
//        service.update(entity);
//
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
