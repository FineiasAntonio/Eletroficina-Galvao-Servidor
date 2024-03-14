package com.eletroficinagalvao.controledeservico.Controller;

import java.util.List;

import com.eletroficinagalvao.controledeservico.Service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable int id){
        Funcionario funcionario = service.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(funcionario);
    }

    @PostMapping
    public ResponseEntity<Funcionario> create(Funcionario funcionario){
        Funcionario funcionarioCriado = service.create(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioCriado);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(int id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping
    public ResponseEntity<Funcionario> update(int id, Funcionario funcionario){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

}
