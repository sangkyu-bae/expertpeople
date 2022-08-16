import React from 'react';
import {Link} from "react-router-dom";
import {faBell,faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function FalseLogin(props) {
    return (
        <>


            {/*<li className="nav-items">*/}
            {/*    <Link to="/" onClick={props.logout}>*/}
            {/*        logout*/}
            {/*    </Link>*/}
            {/*</li>*/}
            <ul className="nav-item flex">
                <li className="nav-items">
                    <FontAwesomeIcon className="bell" icon={faBell}></FontAwesomeIcon>
                </li>
                <li className="nav-items">
                    <FontAwesomeIcon className="bell" icon={faAddressCard}></FontAwesomeIcon>
                </li>
            </ul>
            <div className="drop-wrap">
                <ul className="drop-box">
                    <li className="border-bottom" >
                        {props.name}
                    </li>
                    <li className="border-bottom" onClick={props.logout}>
                        logout
                    </li>
                    <li>
                        설정
                    </li>
                </ul>
            </div>
        </>
    );
}

export default FalseLogin;