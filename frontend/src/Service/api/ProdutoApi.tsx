import { toast } from "react-toastify";
import { NovoProduto, Produto } from "../Entities/Produto";
import { API } from "./api";

export async function getAllProduto(): Promise<Produto[]> {
    const response = await API.get("/estoque")

    if (response.status !== 200) {

        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.data as Produto[]
}

export async function createProduto(produto: NovoProduto): Promise<number> {
    const response = await API.post("/estoque", produto)

    if (response.status !== 201) {
        
        
        toast.error(`${response.status} - ${response.data}`)

        throw {
            status: response.status,
            errorData: response.data,
        };

    }
    return response.status
}

export async function deleteProduto(id: string): Promise<number> {
    const response = await API.delete(`/estoque/${id}`)

    
    if (response.status !== 200) {

        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.status
} 