import React, {useEffect, useState} from 'react';
import './Main.css';
import {Link, useNavigate} from 'react-router-dom';
import commnMethod from "../util/common/CommnMethod";
import {useDispatch, useSelector} from "react-redux";
import {logoutUser} from "../util/Redux/userReducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHelmetSafety} from "@fortawesome/free-solid-svg-icons";
import TrueLogin from "../LoginComponent/isLoginComponent/TrueLogin";
import FalseLogin from "../LoginComponent/isLoginComponent/FalseLogin";
import {requestURL} from "../util/common/RequestUrl";


function MainNav(props) {
    const dispatch = useDispatch();
    const isLogin = useSelector(state => state.userReducer.isLogin);
    const userName = useSelector(state => state.userReducer.user);
    const [event,setEvent]=useState({
        isConnection:false,
        eventSource:[]
    })
    const {isConnection,eventSource}=event;
    const navigate = useNavigate();
    const logout = () => {
        localStorage.removeItem("jwt");

        setEvent({
            isConnection: false,
            eventSource: []
        })

        dispatch(logoutUser());
        navigate("/");
    }
    useEffect(() => {
        if(isLogin&&!isConnection){
            fetchSse(userName.userId);
        }
    }, [isLogin])
    const fetchSse = async (userId) => {
        try {
            let eventSource
            eventSource = new EventSource(`${requestURL}api/notify/${userId}`)
            eventSource.onopen = event => {
                console.log(event)
                console.log("connection opened");
            };
            eventSource.addEventListener('sse', function (e) {
                console.log("sse")
            }, false);
            eventSource.addEventListener('newWork', function (e) {
                console.log("알림 발생~~")
            }, false);
            eventSource.onmessage = async (event) => {
                const res = await event.data;
            };
            eventSource.onerror = async (event) => {
                console.log(eventSource)
                if (!event.error.message.includes("No activity"))
                    eventSource.close();
            };
            setEvent({
                isConnection:true,
                eventSource: eventSource
            })
        } catch (error) {
            console.log(error)
        }
    }
    return (
        <nav className="navbar">
            <div className="head-wrap flex">
                <div className="main">
                    <Link to="/">
                        <FontAwesomeIcon className="icons" icon={faHelmetSafety}/>
                    </Link>
                </div>
                <div className="item ">
                    {!isLogin &&
                        <TrueLogin></TrueLogin>
                    }
                    {isLogin &&
                        <FalseLogin name={userName.name} logout={logout}></FalseLogin>
                    }
                </div>
            </div>
        </nav>
    );
}

export default MainNav;