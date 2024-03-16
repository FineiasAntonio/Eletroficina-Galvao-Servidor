import { useState } from "react";
import OSForm from "./OSForm";
import OrcamentoForm from "./OrcamentoForm";
import { Reserva, produtosReservados } from "../../Service/Entities/Reserva";
import { NovoProduto } from "../../Service/Entities/Produto";
import { OSCreateRequest } from "../../Service/Entities/OS";
import { createOS } from "../../Service/api/OSapi";
import { useNavigate } from "react-router-dom";

export default function Form() {

  const navigate = useNavigate();

  const [reserva, setReserva] = useState<Reserva>();
  const [osRequest, setOsRequest] = useState<OSCreateRequest>();
  const [imagens, setImagens] = useState<Blob[]>([]);

  console.log(imagens)

  const setarReserva = (produtosExistentesInput: produtosReservados[], produtosNovosInput: NovoProduto[], maoDeObraInput: number) => {
    const Reserva = {
      produtosExistentes: produtosExistentesInput,
      produtosNovos: produtosNovosInput,
      maoDeObra: maoDeObraInput
    }
    setReserva(Reserva);
  }

  const setarOS = (OSRequest: OSCreateRequest) => {
    setOsRequest(OSRequest);
  }

  const adicionarImagens = (novasImagens: Blob[]) => {
    setImagens((prevData) => [...prevData, ...novasImagens]);
  };

  const envia = async () => {
    const request = {
      nome: osRequest?.nome,
      telefone: osRequest?.telefone,
      endereco: osRequest?.endereco,
      cpf: osRequest?.cpf,
      equipamento: osRequest?.equipamento,
      numeroSerie: osRequest?.numeroSerie,
      servico: osRequest?.servico,
      dataSaida: osRequest?.dataSaida,
      funcionarioId: osRequest?.funcionarioId,
      observacao: osRequest?.observacao,
      comentarios: osRequest?.comentarios,
      reserva: reserva
    }

    createOS(request);
    navigate("/")
    /* if (response.id) {
      uploadImages(response.id, imagens, 1);
    } */
    
  }

  return (
    <div className="w-screen-md mx-auto">

      <div className="mt-4 p-4 bg-gray-100">
        <OSForm setarOS={setarOS} adicionarImagens={adicionarImagens} />
        <OrcamentoForm setarReserva={setarReserva} />
      </div>
      <hr />
      <div className=" flex items-center justify-end gap-x-6 p-8 bg-gray-100">
        <button type="button" className="text-sm font-semibold leading-6 text-gray-900" onClick={() => {navigate("/")}}>
          Cancelar
        </button>
        <button
          onClick={envia}
          type="submit"
          className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
        >
          Salvar
        </button>
      </div>
    </div>
  )
}