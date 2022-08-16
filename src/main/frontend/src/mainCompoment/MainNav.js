import React from 'react';
import './Main.css';
import { Link, useNavigate } from 'react-router-dom';
import commnMethod from "../util/common/CommnMethod";
import {useDispatch, useSelector} from "react-redux";
import {logoutUser} from "../util/Redux/userReducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import{faHelmetSafety} from "@fortawesome/free-solid-svg-icons";
import TrueLogin from "../LoginComponent/isLoginComponent/TrueLogin";
import FalseLogin from "../LoginComponent/isLoginComponent/FalseLogin";


function MainNav(props) {
    const dispatch=useDispatch();
    const isLogin=useSelector(state=> state.userReducer.isLogin);
    const userName=useSelector(state=>state.userReducer.user);
    const navigate=useNavigate();
    const logout=()=>{
        localStorage.removeItem("jwt");
        dispatch(logoutUser());
        navigate("/");
    }
    return (
        <nav className="navbar">
            <div className="head-wrap flex">
                <div className="main">
                    <Link to="/">
                        <FontAwesomeIcon icon={faHelmetSafety} />
                    </Link>
                </div>
                <div className="item ">
                        {!isLogin &&
                         <TrueLogin></TrueLogin>
                        }
                        {isLogin &&
                            <FalseLogin name ={userName.name} logout={logout}></FalseLogin>
                        }
                </div>
            </div>
        </nav>
    );
}

export default MainNav;