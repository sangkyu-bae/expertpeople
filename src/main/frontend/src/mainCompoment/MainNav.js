import React, {useEffect, useState} from 'react';
import './Main.css';
import {Link, useNavigate} from 'react-router-dom';
import {useDispatch, useSelector} from "react-redux";
import {logoutUser} from "../util/Redux/userReducer";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faHelmetSafety} from "@fortawesome/free-solid-svg-icons";
import TrueLogin from "../LoginComponent/isLoginComponent/TrueLogin";
import FalseLogin from "../LoginComponent/isLoginComponent/FalseLogin";
import Form from "react-bootstrap/Form";

function MainNav({search, searchChange, searchSubmit, changeIsSearch, isSearch}) {
    const dispatch = useDispatch();
    const isLogin = useSelector(state => state.userReducer.isLogin);
    const userName = useSelector(state => state.userReducer.user);
    const [event, setEvent] = useState({
        isConnection: false,
        eventSource: []
    })
    const {isConnection, eventSource} = event;
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

    const nav = useNavigate();
    useEffect(() => {
        if (isSearch) nav("/")
    }, [isSearch])
    return (
        <nav className="navbar">
            <div className="head-wrap flex">
                <div className="main">
                    <Link to="/" onClick={changeIsSearch}>
                        <FontAwesomeIcon className="icons" icon={faHelmetSafety}/>
                    </Link>
                    {
                        isLogin &&
                        <Form className="search_form" onSubmit={searchSubmit}>
                            <input className="search_input" type="search" value={search} onChange={searchChange}/>
                        </Form>
                    }

                </div>
                <div className="item ">
                    {
                        isLogin ?
                            <FalseLogin name={userName.name} logout={logout}></FalseLogin> :
                            <TrueLogin></TrueLogin>
                    }

                </div>
            </div>
        </nav>
    );
}

export default MainNav;