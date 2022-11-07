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
import { NativeEventSource, EventSourcePolyfill } from 'event-source-polyfill';


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