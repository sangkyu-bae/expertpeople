import React, {useEffect, useState} from 'react';
import {faAddressCard,faPersonDigging,faEnvelope,faCalendarCheck,faLocationArrow} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import "./Profile.css";
import ProfileLeft from "../SettingCommon/ProfileLeft";
import ProfieRigth from "../SettingCommon/ProfieRigth";
import {useDispatch, useSelector} from "react-redux";
import CommnMethod from "../../util/common/CommnMethod";
import axiosCo from "../../util/common/axiosCommon";
function Profile(props) {
    const login=useSelector(state=> state.userReducer.user);
    const [userInfo,setUserInfo]=useState([]);

    useEffect(()=>{
       const checkAuth=CommnMethod.checkAuth(login.id);
       if(checkAuth){
           const res=axiosCo.myInfo(login.id)
           res.then(e=>{
               console.log(e);
               setUserInfo(e.data)
           })
               .catch(e=>console.log(e));
       }
    },[])

    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    {userInfo &&
                        <>
                        <ProfileLeft userImage={userInfo.profileImage}></ProfileLeft>
                        <ProfieRigth userInfo={userInfo}></ProfieRigth>
                        </>
                    }
                </div>
            </div>
        </div>
    );
}

export default Profile;
