import './App.css'
import Dash from './Components/Dash'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import UpdateForm from './Components/Form/UpdateOSForm/UpdateForm'
import NavBar from './Components/NavBar'
import Form from './Components/Form/Form'
import EstoqueTable from './Components/Estoque/EstoqueTable'
import { ToastContainer } from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css';

function App() {

  return (
    <>
    <NavBar/>
    <ToastContainer
position="bottom-right"
autoClose={5000}
hideProgressBar={false}
newestOnTop={false}
closeOnClick
rtl={false}
pauseOnFocusLoss={false}
draggable
pauseOnHover={false}
theme="light"
/>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Dash/>}/>
        <Route path="/update/:id" element={<UpdateForm/>} />
        <Route path="/create" element={<Form/>} />
        <Route path="/estoque" element={<EstoqueTable/>}/>
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
