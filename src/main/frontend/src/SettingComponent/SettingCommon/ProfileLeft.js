import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import UserImg from "./UserImg";
import {Link} from "react-router-dom";

function ProfileLeft({click}) {

    return (
        <div className="left">

            <UserImg></UserImg>
            <div className="itme-wrap">
                <ul className="my-item">
                    <Link to='/myprofile'>
                        <li className={`items ${click == 'info' && "click"}`}>
                            <span>소개</span>
                        </li>
                    </Link>
                    <Link to='/myprofile/admin-work'>
                        <li className={`items ${click == 'admin' && "click"}`}>
                            <span>관리 일감</span>
                        </li>
                    </Link>
                </ul>
            </div>
        </div>
    );
}

export default ProfileLeft;
