import React from 'react';
import './Main.css';
import { Link, useNavigate } from 'react-router-dom';

function MainNav(props) {
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
                        <li className="nav-items">
                            <Link to="/login">
                                login
                            </Link>
                        </li>
                        <li className="nav-items">
                            <form method="post">
                                <button>로그아웃</button>
                            </form>
                        </li>
                        <li className="nav-items">
                            <Link to="/join-up">
                                join
                            </Link>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    );
}

export default MainNav;