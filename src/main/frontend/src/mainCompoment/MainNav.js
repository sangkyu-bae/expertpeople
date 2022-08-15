import React from 'react';
import './Main.css';
import { Link, useNavigate } from 'react-router-dom';
import commnMethod from "../util/common/CommnMethod";
import {useDispatch, useSelector} from "react-redux";
import {logoutUser} from "../util/Redux/userReducer";
import { IconName } from "react-icons/bs";
function MainNav(props) {
    const dispatch=useDispatch();
    const isLogin=useSelector(state=> state.userReducer.isLogin);
    const logout=()=>{
        localStorage.removeItem("jwt");
        dispatch(logoutUser());
    }
    return (
        <nav className="navbar">
            <div className="head-wrap flex">
                <div className="main">
                    <Link to="/">
                        ExpertPeople
                    </Link>
                </div>
                <div className="item ">
                    <ul className="nav-item flex">
                        {!isLogin &&
                            <li className="nav-items">
                                <Link to="/login">
                                    login
                                </Link>
                            </li>
                        }
                        {isLogin &&
                            <li className="nav-items">
                                <Link to="/" onClick={logout}>
                                    logout
                                </Link>
                            </li>
                        }
                        {!isLogin&&
                            <li className="nav-items">
                                <Link to="/join-up">
                                    join
                                </Link>
                            </li>
                        }
                        <BsFillFileEarmarkPersonFill></BsFillFileEarmarkPersonFill>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default MainNav;