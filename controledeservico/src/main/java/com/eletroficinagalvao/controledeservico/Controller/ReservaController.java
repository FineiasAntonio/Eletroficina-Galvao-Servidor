package com.eletroficinagalvao.controledeservico.Controller;

import com.eletroficinagalvao.controledeservico.Domain.DTO.Reserva.ReservaProdutoExistenteDTO;
import com.eletroficinagalvao.controledeservico.Domain.Entity.Reserva;
import com.eletroficinagalvao.controledeservico.Service.ReservaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @GetMapping()
    public ResponseEntity<List<Reserva>> listar() {
        return new ResponseEntity<>(reservaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/{os}")
    public ResponseEntity<Void> reservarProduto(@PathVariable int os, @RequestBody ReservaProdutoExistenteDTO produto){
        reservaService.reservarProdutoDoEstoque(os, produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
