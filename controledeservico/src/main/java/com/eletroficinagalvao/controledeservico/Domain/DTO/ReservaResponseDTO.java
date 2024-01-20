package com.eletroficinagalvao.controledeservico.Domain.DTO;

import java.util.List;
import com.eletroficinagalvao.controledeservico.Domain.Entity.ProdutoReservado;

public record ReservaResponseDTO(
    String uuid,
    List<ProdutoReservado> produtos,
    boolean ativo

) {
    
}
