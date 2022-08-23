import React, {useRef, useState} from 'react';
import {Link} from "react-router-dom";
import {faBell,faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function FalseLogin(props) {
    const menu=useRef();
    const [isMenu,setIsMenu]=useState(false);

    const clickMyMenuBar=()=>{
        if(!isMenu){
            menu.current.style="display:block";
            setIsMenu(true);
        }else{
            menu.current.style="display:none";
            setIsMenu(false);
        }
    }

    return (
        <>
            <ul className="nav-item flex">
                <li className="nav-items">
                    <FontAwesomeIcon className="bell" icon={faBell}></FontAwesomeIcon>
                </li>
                <li className="nav-items" onClick={()=>clickMyMenuBar()}>
                    <FontAwesomeIcon className="bell" icon={faAddressCard}></FontAwesomeIcon>
                </li>
            </ul>
            <div className="drop-wrap" ref={menu}>
                <ul className="drop-box">
                    <div className="head_info">
                        <li className="name" >
                            <span>{props.name}</span>
                        </li>
                        <li className="border-bottom profile" >
                            <span><Link to="/myprofile">프로필</Link></span>
                        </li>
                    </div>
                    <div className="setting">
                        <li  onClick={props.logout}>
                            <span>logout</span>
                        </li>
                        <li>
                            <span>
                                <Link to="/setting/profile">
                                    설정
                                </Link>
                           </span>
                        </li>
                    </div>

                </ul>
            </div>
        </>
    );
}

export default FalseLogin;