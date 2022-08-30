import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {useSelector} from "react-redux";

function UserImg(props) {
    const login = useSelector(state => state.userReducer.user);
    const [userImage,setUserImage]=useState("");
    useEffect(()=>{
        setUserImage(login.profileImage);
    },[])
    return (
        <div className="profile-img-wrap">
            {userImage?
                <img src={userImage}/>:
                <FontAwesomeIcon className="img" icon={faAddressCard}></FontAwesomeIcon>
            }

        </div>
    );
}

export default UserImg;