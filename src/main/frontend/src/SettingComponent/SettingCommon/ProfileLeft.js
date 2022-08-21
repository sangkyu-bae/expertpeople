import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";

function ProfileLeft(props) {
    const [userImage,setUserImage]=useState("");
    useEffect(()=>{
        setUserImage(props.userImage)
    },[])
    return (
        <div className="left">
            <div className="profile-img-wrap">
                {userImage?
                    <img src={userImage}/>:
                    <FontAwesomeIcon className="img" icon={faAddressCard}></FontAwesomeIcon>
                }

            </div>
            <div className="itme-wrap">
                <ul className="my-item">
                    <li className="items click">
                        <span>
                            소개
                        </span>
                    </li>
                    <li className="items">
                        <span>
                            스터디
                        </span>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default ProfileLeft;
