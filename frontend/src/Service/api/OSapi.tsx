import { API } from "./api";
import { OrdemServico, UpdateOrdemServicoDTO } from "../Entities/OS";
import { NotificationBody } from "../Entities/Notification";
import { toast } from "react-toastify";

export async function getAllOS(): Promise<OrdemServico[]> {
    const response = await API.get("/ordensdeservicos")

    if (response.status !== 200) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.data as OrdemServico[]
}

export async function getOSById(id?: string) {
    const response = await API.get(`/ordensdeservicos/${id}`)

    if (response.status !== 200) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.data as OrdemServico
}

export async function createOS(OS: any): Promise<OrdemServico> {
    const response = await API.post("/ordensdeservicos", OS, {
        timeout: 60000,
    });

    if (response.status !== 201) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data,
        };
    }

    return response.data as OrdemServico;
}

export async function deleteOS(id?: number) {
    if(id === undefined){
        throw new Error("Id undefined");
    }
    const response = await API.delete(`/ordensdeservicos/${id}`)

    if (response.status !== 204) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errordata: response.data
        }
    }
}

export async function updateOS(id: number, os: UpdateOrdemServicoDTO): Promise<number> {

    console.log(os);
    const response = await API.put(`/ordensdeservicos/${id}`, os);

    if (response.status === 201){
        toast.error(`${response.status} - ${response.data}`)
        console.log('atualizado')
    }
    
    return response.status;
}

export async function getNotifications(): Promise<NotificationBody[]> {
    const response = await API.get("/notifications")

    if (response.status !== 200) {
        toast.error(`${response.status} - ${response.data}`)
        throw {
            status: response.status,
            errorData: response.data
        };
    }

    return response.data as NotificationBody[]
}

//FIX
export async function uploadImages(id: number, images: Blob[], method: number) {
    const formData = new FormData();

    images.forEach((image) => {
        formData.append("imagens", image);
    });

    console.log(images)

    try {
        const response = await API.post(`/ordensdeservicos/image/${id}?method=${method}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            timeout: 60000, // Define o timeout para 60 segundos (ou ajuste conforme necessário)
        });

        if (response.status !== 200) {
            throw {
                status: response.status,
                errorData: response.data
            };
        }
    } catch (error) {
        // Trate o erro, como rethrow ou log
        console.error('Erro na requisição:', error);
        throw error;
    }
}