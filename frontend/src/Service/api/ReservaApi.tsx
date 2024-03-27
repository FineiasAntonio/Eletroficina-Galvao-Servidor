import { toast } from "react-toastify";
import { reserva } from "../Entities/OS";
import { produtosReservados } from "../Entities/Reserva";
import { API } from "./api";

export async function getAllReserva(): Promise<reserva[]> {
    const response = await API.get("/reservas")

    if (response.status !== 200) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.data as reserva[]
}

export async function reservarProduto(id: number, produto: produtosReservados): Promise<number> {
    const response = await API.post(`/reservas/${id}`, produto)

    if (response.status !== 200) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.status
}