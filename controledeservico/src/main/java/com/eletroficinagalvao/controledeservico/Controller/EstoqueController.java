package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Estoque.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Mapper.ProdutoMapper;
import com.eletroficinagalvao.controledeservico.Service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping ("/estoque")
public class EstoqueController {

    @Autowired
    @Qualifier ("EstoqueService")
    private EstoqueService service;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable String id){
        return new ResponseEntity<>(service.getById(UUID.fromString(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProdutoDTO produtos){
            service.create(produtos);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Produto produto){
        service.update(UUID.fromString(id), produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
