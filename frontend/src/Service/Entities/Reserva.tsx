import { NovoProduto } from "./Produto"

export interface Reserva{
    produtosExistentes?: produtosReservados[]
    produtosNovos?: NovoProduto[]
    maoDeObra?: number
}

export interface produtosReservados{
    uuidProduto: string | undefined
    quantidade: number | undefined
}


