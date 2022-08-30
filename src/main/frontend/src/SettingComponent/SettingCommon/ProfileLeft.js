import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import UserImg from "./UserImg";

function ProfileLeft(props) {

    return (
        <div className="left">
            <UserImg></UserImg>
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
