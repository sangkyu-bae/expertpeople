import React, {useEffect, useState} from 'react';
import ProfileLeft from "../SettingCommon/ProfileLeft";
import ProfieRigth from "../SettingCommon/ProfieRigth";
import "./MyProfile.css";
import LeftMyProfile from "./LeftMyProfile";
import CenterMyProfile from "./CenterMyProfile";
import {useSelector} from "react-redux";
import UserImg from "../SettingCommon/UserImg";
import RigthMyProfile from "./RigthMyProfile";
function MyProfile(props) {
    const login=useSelector(state => state.userReducer.user);
    const [userInfo,setUserInfo]=useState([]);

    useEffect(()=>{
        console.log(login)
    },[])
    const [inputs,setInputs]=useState({
        bio:'',
        job:'',
        profileImage:'',
        location:''
    })

    const{bio,job,profileImage,location}=inputs;

    const onChange=e=>{
        const{name,value}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        });
    }

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">

                        <LeftMyProfile></LeftMyProfile>
                        <CenterMyProfile></CenterMyProfile>
                        {/*<RigthMyProfile></RigthMyProfile>*/}

                </div>
            </div>
        </div>
    );
}

export default MyProfile;