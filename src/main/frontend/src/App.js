import './App.css';
import MainNav from "./mainCompoment/MainNav";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Login from "./LoginComponent/Login";
import Index from './mainCompoment/Index';
import JoinUp from "./accountComponeent/JoinUp";
import Footer from "./mainCompoment/Footer";
import Profile from "./SettingComponent/ProfileComponet/Profile";
import MyProfile from "./SettingComponent/MyProfile/MyProfile";
import PasswordUpdate from "./SettingComponent/ProfilePassWord/PasswordUpdate";
import AttentionTopic from "./SettingComponent/AttentionTopic/AttentionTopic";
import MyLocation from "./SettingComponent/MyLocation/MyLocation";
import NewWork from "./WorkComponent/NewWork/NewWork";
import Work from "./WorkComponent/WorkInfo/Work";
import WorkMembers from "./WorkComponent/WorkInfo/WorkMembers";
import WorkSetting from "./WorkComponent/WorkSetting/WorkSetting";
import SettingJob from "./WorkComponent/WorkSetting/Job/SettingJob";
import SettingLocation from "./WorkComponent/WorkSetting/Location/SettingLocation";
import WorkInfo from "./WorkComponent/WorkInfoSetting/WorkInfo";
import WorkRecuitment from "./WorkComponent/Recruitment/WorkRecuitment";
import NewRecruitment from "./WorkComponent/Recruitment/NewRecruitment";

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
                    <Route path="/setting/password" element={<PasswordUpdate></PasswordUpdate>}/>
                    <Route path="/setting/attention-topic" element={<AttentionTopic></AttentionTopic>}></Route>
                    <Route path="/setting/location" element={<MyLocation></MyLocation>}></Route>
                    <Route path="/new-work" element={<NewWork></NewWork>}></Route>
                    <Route path="/work/:path" element={<Work></Work>}></Route>
                    <Route path="/work/:path/members" element={<WorkMembers></WorkMembers>}></Route>
                    <Route path="/work/:path/setting/info" element={<WorkSetting></WorkSetting>}></Route>
                    <Route path="/work/:path/setting/job" element={<SettingJob></SettingJob>}></Route>
                    <Route path="/work/:path/setting/location" element={<SettingLocation></SettingLocation>}></Route>
                    <Route path="/work/:path/setting" element={<WorkInfo></WorkInfo>}></Route>
                    <Route path="/work/:path/recruitment/:id" element={<WorkRecuitment></WorkRecuitment>}></Route>
                    <Route path="/work/:path/new-recruitment" element={<NewRecruitment></NewRecruitment>}></Route>
                </Routes>
            <Footer></Footer>
        </BrowserRouter>
    );
}

export default App;
