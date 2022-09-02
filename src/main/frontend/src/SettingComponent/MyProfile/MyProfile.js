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
    // const [userInfo,setUserInfo]=useState([]);
    //
    useEffect(()=>{
        console.log(login)
    },[])
    // const [inputs,setInputs]=useState({
    //     bio:'',
    //     job:'',
    //     profileImage:'',
    //     location:''
    // })
    //

    const[bio,setBio]=useState("");
    const[job,setJob]=useState("");
    const[profileImage,setProfileImage]=useState("");
    const[location,setLocation]=useState("");

    const changeBio=e=>setBio(e.target.value);
    const changeJob=e=>setJob(e.target.value);
    const changeProfileImage=e=>setProfileImage(e.target.value);
    const changeLocation=e=>{
        let reader=new FileReader();
        reader.readAsDataURL(e.target.files[0]);
        reader.onload=()=>setLocation(reader.result);
    }
    // const{bio,job,profileImage,location}=inputs;


    // const onChange=e=>{
    //     const{name,value}=e.target;
    //     setInputs({
    //         ...inputs,
    //         [name]:value
    //     });
    // }
    const onSubmit=e=>{
        e.preventDefault();

    }

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">

                        <LeftMyProfile></LeftMyProfile>
                        <CenterMyProfile changeJob={changeJob}
                                         changeBio={changeBio}
                                         changeLocation={changeLocation}
                                         bio={bio}
                                         job={job}
                                         location={location}
                                         onSubmit={onSubmit}
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