import './App.css';
import MainNav from "./mainCompoment/MainNav";
import {BrowserRouter, Routes, Route, useNavigate} from "react-router-dom";
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
import RecruitmentList from "./WorkComponent/Recruitment/RecruitmentList";
import UpdateRecruit from "./WorkComponent/Recruitment/UpdateRecruit";
import Notification from "./NotificationComponent/Notification";
import OldNotification from "./NotificationComponent/OldNotification";
import {useReducer, useState} from "react";
import axiosCo from "./util/common/axiosCommon";
import {useSelector} from "react-redux";
import LoginRoute from "./CommonComponent/LoginRoute";
import AdminWork from "./SettingComponent/MyProfile/AdminWork";
import SettingMyNotification from "./SettingComponent/SettingMyNotification/SettingMyNotification";


function App() {
    const [searchData,setSearchData]=useState([]);
    const [search,setSearch]=useState("");

    const searchSubmit=(e)=>{
        e.preventDefault();
        axiosCo.getSearchWork(search)
            .then(e=>{
                setSearchData(e.data);
                setSearch("");
                setIsSearch(true)
            })
            .catch(e=>{
                console.log(e)
            })
    }
    const searchChange=(e)=>{
        setSearch(e.target.value);
    }
    const [isSearch, setIsSearch] = useState(false);
    const changeIsSearch=()=>{
        isSearch&&
        setIsSearch(false)
    }
    const isLogin=useSelector(state=>state.userReducer.isLogin);
    return (
        <BrowserRouter>
            <MainNav
                search={search}
                searchSubmit={searchSubmit}
                searchChange={searchChange}
                changeIsSearch={changeIsSearch}
                isSearch={isSearch}
            ></MainNav>
                <Routes>
                    <Route path="/" element={<Index
                        searchData={searchData}
                        isSearch={isSearch}
                    ></Index>}/>
                    <Route path="/login" element={<Login></Login>}/>
                    <Route path="/join-up" element={<JoinUp></JoinUp>}/>
                    <Route path="/myprofile" element={<LoginRoute isLogin={isLogin} component={<Profile/>}/> }/>
                    <Route path="/myprofile/admin-work" element={<LoginRoute isLogin={isLogin} component={<AdminWork/>}/> }/>
                    <Route path="/setting/profile" element={<LoginRoute isLogin={isLogin} component={<MyProfile/>}/> }/>
                    <Route path="/setting/notification" element={<LoginRoute isLogin={isLogin} component={<SettingMyNotification/>}/> }/>
                    <Route path="/setting/password"  element={<LoginRoute isLogin={isLogin} component={<PasswordUpdate/>}/>}/>
                    <Route path="/setting/attention-topic" element={<LoginRoute isLogin={isLogin} component={<AttentionTopic/>}/>}></Route>
                    <Route path="/setting/location" element={<LoginRoute isLogin={isLogin} component={<MyLocation/>}/>} ></Route>
                    <Route path="/new-work" element={<LoginRoute isLogin={isLogin} component={<NewWork/>}/>}></Route>
                    <Route path="/work/:path" element={<LoginRoute isLogin={isLogin} component={<Work/>}/>}></Route>
                    <Route path="/work/:path/members" element={<LoginRoute isLogin={isLogin} component={<WorkMembers/>}/>}></Route>
                    <Route path="/work/:path/setting/info" element={<LoginRoute isLogin={isLogin} component={<WorkSetting/>}/>}></Route>
                    <Route path="/work/:path/setting/job" element={<LoginRoute isLogin={isLogin} component={<SettingJob/>}/>} ></Route>
                    <Route path="/work/:path/setting/location" element={<LoginRoute isLogin={isLogin} component={<SettingLocation/>}/>} ></Route>
                    <Route path="/work/:path/setting" element={<LoginRoute isLogin={isLogin} component={<WorkInfo/>}/>} ></Route>
                    <Route path="/work/:path/recruitment/:id" element={<LoginRoute isLogin={isLogin} component={<WorkRecuitment/>}/>}></Route>
                    <Route path="/work/:path/new-recruitment" element={<LoginRoute isLogin={isLogin} component={<NewRecruitment/>}/>}></Route>
                    <Route path="/work/:path/recruitment" element={<LoginRoute isLogin={isLogin} component={<RecruitmentList/>}/>}></Route>
                    <Route path="/work/:path/update-recruitment/:id" element={<LoginRoute isLogin={isLogin} component={<UpdateRecruit/>}/>}></Route>
                    <Route path="/notification/new" element={<LoginRoute isLogin={isLogin} component={<Notification/>}/>} ></Route>
                    <Route path="/notification/old" element={<LoginRoute isLogin={isLogin} component={<OldNotification/>}/>}></Route>
                </Routes>
            <Footer></Footer>
        </BrowserRouter>
    );
}

export default App;
