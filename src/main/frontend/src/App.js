import './App.css';
import MainNav from "./mainCompoment/MainNav";
import { BrowserRouter,Routes,Route  } from "react-router-dom";
import Login from "./LoginComponent/Login";
import Index from './mainCompoment/Index';
import JoinUp from "./accountComponeent/JoinUp";
import Footer from "./mainCompoment/Footer";
import Profile from "./SettingComponent/ProfileComponet/Profile";
import MyProfile from "./SettingComponent/MyProfile/MyProfile";

function App() {
  return (
      <BrowserRouter>
        <MainNav></MainNav>
            <Routes>
              <Route path="/" element={<Index></Index>}/>
              <Route path="/login" element={<Login></Login>}/>
              <Route path="/join-up" element={<JoinUp></JoinUp>}/>
              <Route path="/myprofile" element={<Profile></Profile>}/>
              <Route path="/setting/profile" element={<MyProfile></MyProfile>}/>
            </Routes>
        <Footer></Footer>
      </BrowserRouter>
  );
}

export default App;
