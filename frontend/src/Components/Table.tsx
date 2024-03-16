import { useEffect, useState } from "react";
import { OrdemServico } from "../Service/Entities/OS";
import { getAllOS } from "../Service/api/OSapi";
import { format } from "date-fns"
import { FaRegEye, FaSearch } from "react-icons/fa";
import ModalOS from "./ModalOS";
import { useNavigate } from "react-router-dom";

export default function Table() {

  const navigate = useNavigate();

  const [ordemSelecionada, setOrdemSelecionada] = useState<OrdemServico>();
  const [isModalOpen, setModalOpen] = useState<boolean>(false);
  const [data, setData] = useState<OrdemServico[]>([]);
  // depois botar esse const pra ser o filtered
  const [Ndata, NsetData] = useState<OrdemServico[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const osData = await getAllOS();
        setData(osData);
        NsetData(osData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  const selecionarOS = (id: number) => {
    setOrdemSelecionada(data.find(e => e.id == id));
    setModalOpen(true);
  }

  const fecharModal = () => {
    setModalOpen(false);

  }

  const handlePesquisa = (e: React.ChangeEvent<HTMLInputElement>) => {
    const pesquisaInput = e.target.value;
    const dadosFiltrados = Ndata.filter((os) => {
      return Number(pesquisaInput) ? os.id.toString().includes(pesquisaInput) : os.nome.includes(pesquisaInput)
    })

    setData(pesquisaInput.trim() === "" ? Ndata : dadosFiltrados)
  }

  return (
    <>
      <div className="min-h-full">
        <header className="bg-white shadow">
          <div className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8 flex justify-between">
            <h1 className="text-3xl font-bold tracking-tight text-gray-900">Ordens de serviço</h1>
            <div className="ml-80 mt-1 rounded whitespace-nowrap bg-gray-100 flex">
              <input type="text" placeholder="Pesquisar" className="w-full" onChange={handlePesquisa} />
              <button className="mx-2"> <FaSearch/> </button>
            </div>
            <div className='text-right'>
              <button
                onClick={() => navigate(`/create`)}
                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">
                Nova ordem de serviço
              </button>
            </div>
          </div>
        </header>
      </div>

      <div className="mx-auto py-6 sm:px-6 lg:px-8">
        <div className="overflow-x-auto bg-gray-500 shadow-md sm:rounded-lg max-h-tela">
          <table className="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 ">
            <thead className="text-xs text-center text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
              <tr>
                <th scope="col" className="px-6 py-3">
                  OS
                </th>
                <th scope="col" className="px-6 py-3">
                  Cliente
                </th>
                <th scope="col" className="px-6 py-3">
                  Equipamento
                </th>
                <th scope="col" className="px-6 py-3">
                  Data de saída
                </th>
                <th scope="col" className="px-6 py-3">
                  Serviço
                </th>
                <th scope="col" className="px-6 py-3">
                  Técnico
                </th>
                <th scope="col" className="px-6 py-3">
                  Status
                </th>
                <th scope="col" className="px-6 py-3">
                  <span className="sr-only">Edit</span>
                </th>
              </tr>
            </thead>
            <tbody>
              {data.length === 0 ? (
                <tr className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                  <td className="py-4 text-center" colSpan={8}>
                    Sem Dados
                  </td>
                </tr>
              ) : (
                data.map((os) => (
                  <tr key={os.id} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600">
                    <th scope="row" className="px-6 py-4 font-medium text-center text-gray-900 whitespace-nowrap dark:text-white">{os.id}</th>
                    <td className="px-6 py-4">{os.nome}</td>
                    <td className="px-6 py-4 text-center">{os.equipamento}</td>
                    <td className="px-6 py-4 text-center">{format(new Date(os.dataSaida), 'dd/MM/yyyy')}</td>
                    <td className="px-6 py-4 text-center">{os.servico}</td>
                    <td className="px-6 py-4">{os.funcionario.nome}</td>
                    <td className="px-6 py-4 text-center">{os.situacao}</td>
                    <td className="px-6 py-4 text-right">
                      <a href="#" className="font-medium text-blue-600 dark:text-blue-500 hover:underline" onClick={(e) => { e.preventDefault(); selecionarOS(os.id); }}><FaRegEye /></a>
                    </td>
                  </tr>
                ))
              )}
            </tbody>


          </table>
        </div>
      </div>


      <ModalOS OS={ordemSelecionada} isOpen={isModalOpen} onClose={fecharModal} />
    </>
  );
}
