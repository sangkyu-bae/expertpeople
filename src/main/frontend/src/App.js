import './App.css';
import MainNav from "./mainCompoment/MainNav";
import { BrowserRouter,Routes,Route  } from "react-router-dom";
import Login from "./LoginComponent/Login";
import Index from './mainCompoment/Index';
import JoinUp from "./accountComponeent/JoinUp";

function App() {
  return (
      <BrowserRouter>
        <MainNav></MainNav>
        <Routes>
          <Route path="/" element={<Index></Index>}/>
          <Route path="/login" element={<Login></Login>}/>
          <Route path="/join-up" element={<JoinUp></JoinUp>}/>
        </Routes>
      </BrowserRouter>
  );
}

export default App;
