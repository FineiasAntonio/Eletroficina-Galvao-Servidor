import { OrdemServico } from "../Service/Entities/OS";
import "./doc_style.css";
import lojaLogo from "../Assets/loja_logo.png";
import infosRedesLocal from "../Assets/infos_redes_local.png";
import infosContrato from "../Assets/infos_contrato.png";
import { format } from "date-fns/format";
import { useEffect, useState } from "react";
import { getOSById } from "../Service/api/OSapi";
import { useParams } from "react-router-dom";


export default function Doc() {

  let { id } = useParams();

  const [os, setOs] = useState<OrdemServico>();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const osData = await getOSById(id?.toString());
        setOs(osData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
},[])

  return (
    <div id="head">
      <div>
        <img src={lojaLogo} height="120px" width="120px" alt="Loja Logo" />
        <img
          src={infosRedesLocal}
          height="70px"
          width="200px"
          alt="Infos Redes Local"
        />
      </div>

      <h1 style={{ marginLeft: "-70px" }}>Ordem de Serviço</h1>
      <h1>OS: {os?.id}</h1>

      <div id="body1">
        <div style={{ display: "flex", justifyContent: "space-between" }}>
          <div id="info_cliente">
            <p>Cliente: {os?.nome}</p>
            <p>Data de entrada: {os?.dataEntrada ? format(os?.dataEntrada, "dd/MM/yyyy"): ""}</p>
            <p>Telefone: {os?.telefone}</p>
            <p>Equipamento: {os?.equipamento}</p>
            <p>Serviço: {os?.servico}</p>
          </div>
          <div>
            <img
              src={infosContrato}
              height="120px"
              width="300px"
              alt="Infos Contrato"
              id="contrato"
            />
          </div>
        </div>
      </div>

      <hr />

      <div id="body2" style={{ display: "flex", justifyContent: "space-between" }}>
        <img src={lojaLogo} height="120px" width="120px" alt="Loja Logo" />
        <div>
          <h1>Ordem de Serviço</h1>
          <h2>{os?.id}</h2>
        </div>
      </div>
      <div>
        <div style={{ display: "flex" }}>
          <div id="info_cliente" style={{ display: "flex", justifyContent: "space-between" }}>
            <p>Cliente: {os?.nome}</p>
            <p>Data de entrada: {os?.dataEntrada ? format(os?.dataEntrada, "dd/MM/yyyy") : ""}</p>
            <p>Telefone: {os?.telefone}</p>
            <p>Equipamento: {os?.equipamento}</p>
            <p>Serviço: {os?.servico}</p>
          </div>
          <div>OBS: {os?.observacao}</div>
        </div>
      </div>
    </div>
  );
}
