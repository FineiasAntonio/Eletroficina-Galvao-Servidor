package com.eletroficinagalvao.controledeservico.Controller;

import java.util.List;

import com.eletroficinagalvao.controledeservico.Service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eletroficinagalvao.controledeservico.Domain.Entity.Funcionario;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<Funcionario>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getById(@PathVariable int id){
        return new ResponseEntity<Funcionario>(service.getById(String.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(Funcionario funcionario){
        service.create(funcionario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
