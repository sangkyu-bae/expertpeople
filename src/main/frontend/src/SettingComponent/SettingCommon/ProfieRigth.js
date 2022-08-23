import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarCheck, faEnvelope, faLocationArrow, faPersonDigging} from "@fortawesome/free-solid-svg-icons";
import {Link} from "react-router-dom";

function ProfieRigth(props) {
    const [userData,setUserData]=useState([]);
    useEffect(()=>{
        setUserData(props.userInfo);
    },[props.userInfo])

    return (
        <div className="right">
            <div className="my-info-wrap">
                <div className="my-name">{userData.name}</div>
                {userData.bio ?
                    <div className="my-info">{userData.bio}</div>:
                    <div className="my-info">한 줄 소개를 추가하세요.</div>
                }

            </div>
            <div className="join-info-wrap">
                <div className="job">
                    <FontAwesomeIcon className="icons" icon={faPersonDigging}></FontAwesomeIcon>
                    {userData.job?
                        <span>{userData.job}</span>:
                        <span>업종을 등록하세요</span>
                    }

                </div>
                <div className="location">
                    <FontAwesomeIcon className="icons" icon={faLocationArrow}></FontAwesomeIcon>
                    {userData.location?
                        <span>{userData.location}</span>:
                        <span>활동 지역을 등록하세요</span>
                    }

                </div>
                <div className="email">
                    <FontAwesomeIcon className="icons" icon={faEnvelope}></FontAwesomeIcon>
                    <span>{userData.email}</span>
                </div>
                <div className="join-date">
                    <FontAwesomeIcon className="icons" icon={faCalendarCheck}></FontAwesomeIcon>
                    <span>{userData.career} 년차</span>
                </div>
            </div>
           <Link to="/setting/profile">
               <button>프로필 수정</button>
           </Link>

        </div>
    );
}

export default ProfieRigth;