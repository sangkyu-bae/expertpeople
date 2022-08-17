import React, {useEffect} from 'react';
import {faAddressCard,faPersonDigging,faEnvelope,faCalendarCheck,faLocationArrow} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import "./Profile.css";
import ProfileLeft from "../SettingCommon/ProfileLeft";
import ProfieRigth from "../SettingCommon/ProfieRigth";
import {useDispatch} from "react-redux";
function Profile(props) {
    const dispatch=useDispatch();

    useEffect(()=>{

    },[])
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <ProfileLeft></ProfileLeft>
                    <ProfieRigth></ProfieRigth>
                </div>
            </div>
        </div>
    );
}

export default Profile;
