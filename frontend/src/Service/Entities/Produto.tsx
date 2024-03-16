export interface Produto {
    id: string
    produto: string
    referencia: string
    quantidade: number
    precoUnitario: number
}

export interface NovoProduto{
    produto: string | undefined
    referencia: string | undefined
    quantidade: number | undefined
    precoUnitario: number | undefined
}