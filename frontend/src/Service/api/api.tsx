import axios from "axios";

export const API = axios.create({
    baseURL: "http://localhost:8080",
    timeout: 3000,
    timeoutErrorMessage: "Connection timeout",
    validateStatus: () => true
})

export type apiError = {
    errorData: string,
    status: number
}