import { NotificationBody } from "../Service/Entities/Notification"
import { produtosReservados } from "../Service/Entities/Reserva";
import { reservarProduto } from "../Service/api/ReservaApi";

interface NotificationProps {
    notifications: NotificationBody[];
    setNotificacoes: (notificacoes: NotificationBody[]) => void;
}



function NotificationWindow({ notifications, setNotificacoes }: NotificationProps) {

    const reservarProdutoNotification = (id: number, produto: produtosReservados) => {
        reservarProduto(id, produto)
        setNotificacoes(notifications.filter(e => (e.orderID !== id) && e.uuid !== produto.uuidProduto))
    }

    return (
        <div className="fixed w-96 pr-10 pl-10 pb-10 pt-2 mt-20 inset-0 z-50 flex justify-self-end justify-center">
            <div className="bg-wheat p-4 w-full rounded-md shadow-2xl">
                <h2 className="text-center">Notificações</h2>

                {notifications.map((e, index) => (
                    <div key={index} className=" bg-red-500 rounded">
                        <p className="text-sm">A ordem de serviço de {e.nomeCliente.split(" ")[0]} (OS: {e.orderID}) já possui todos os {e.produto} nescessários(a) para ser reservado - Quantidade: {e.quantidade}</p>
                        <button className="bg-gray-200 p-1 rounded outline outline-1 m-1" onClick={() => reservarProdutoNotification(e.orderID, {uuidProduto: e.uuid, quantidade: e.quantidade})}>Reservar agora</button>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default NotificationWindow;