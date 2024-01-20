package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.ProdutoDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Produto;
import com.eletroficinagalvao.controledeservico.Domain.Mapper.ProdutoMapper;
import com.eletroficinagalvao.controledeservico.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/estoque")
public class ProdutoController {

    @Autowired
    @Qualifier ("ProdutoService")
    private ProdutoService service;
    @Autowired
    private ProdutoMapper mapper;

    @GetMapping
    public ResponseEntity<List<Produto>> getAll(){
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable String id){
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody List<ProdutoDTO> produtos){
        for (ProdutoDTO e: produtos) {
            service.create(mapper.map(e));
        }
        
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody Produto produto){
        service.update(id, produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
