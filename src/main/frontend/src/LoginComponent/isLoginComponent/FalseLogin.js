import React, {useEffect, useRef, useState} from 'react';
import {Link} from "react-router-dom";
import {faBell,faAddressCard,faPlus} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useSelector} from "react-redux";
import {requestURL} from "../../util/common/RequestUrl";

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
    const userName = useSelector(state => state.userReducer.user);
    useEffect(() => {
       // fetchSse(userName.userId);
    }, [])
    const [isNotify,setIsNotify]=useState(false);
    const fetchSse = async (userId) => {
        try {
            let eventSource
            const token=`Bearer ${localStorage.getItem("jwt")}`
            eventSource = new EventSource(`${requestURL}api/notify?jwt=${token}`)
            eventSource.onopen = event => {
                console.log(event)
                console.log("connection opened");
            };
            eventSource.addEventListener('init', function (e) {
                if(e.data>0) setIsNotify(true);
            }, false);
            eventSource.addEventListener('newWork', function (e) {
                setIsNotify(true);
            }, false);
            eventSource.onmessage = async (event) => {
                const res = await event.data;
            };
            eventSource.onerror = async (event) => {
                console.log(eventSource)
                if (!event.error.message.includes("No activity"))
                    eventSource.close();
            };
        } catch (error) {
            console.log(error)
        }
    }
    return (
        <>
            <ul className="nav-item flex">
                <li className="nav-items">
                    <Link to="/notification/new">
                        <FontAwesomeIcon className="bell" icon={faBell}></FontAwesomeIcon>
                    </Link>

                </li>
                <li className="nav-items border">
                    <Link to="/new-work">
                            <div className="work-wrap">
                                <FontAwesomeIcon className="bell work" icon={faPlus}></FontAwesomeIcon>
                                <span>일감 개설</span>
                            </div>
                    </Link>
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