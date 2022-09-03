import React, {useEffect, useState} from 'react';
import ProfileLeft from "../SettingCommon/ProfileLeft";
import ProfieRigth from "../SettingCommon/ProfieRigth";
import "./MyProfile.css";
import LeftMyProfile from "./LeftMyProfile";
import CenterMyProfile from "./CenterMyProfile";
import {useSelector} from "react-redux";
import UserImg from "../SettingCommon/UserImg";
import RigthMyProfile from "./RigthMyProfile";
import axiosCo from "../../util/common/axiosCommon";
import {useNavigate} from "react-router-dom";
function MyProfile(props) {

    const [inputs,setInputs]=useState({
        bio:'',
        job:'',
        profileImage:'',
        location:''
    })

    const changeProfileImage=e=>{
        let reader=new FileReader();
        reader.readAsDataURL(e.target.files[0]);
        reader.onload=()=>setInputs({
            ...inputs,
            profileImage:reader.result
        })
    }


    const{bio,job,profileImage,location}=inputs;
    const onChange=e=>{
        const{name,value}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        });
    }
    const nav=useNavigate();

    const onSubmit=e=>{
        e.preventDefault();
        const updateProfile=axiosCo.updateProfile(inputs)
        updateProfile.then(e=>{
            console.log(e);
            //nav('/Profile');
        })
                    .catch(e=>console.log(e));
    }
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">

                        <LeftMyProfile></LeftMyProfile>
                        <CenterMyProfile
                                         bio={bio}
                                         job={job}
                                         location={location}
                                         onSubmit={onSubmit}
                                         onChange={onChange}
                        ></CenterMyProfile>
                        <RigthMyProfile
                            profileImage={profileImage}
                             changeProfileImage={changeProfileImage}
                        ></RigthMyProfile>

                </div>
            </div>
        </div>
    );
}

export default MyProfile;