import React, {useEffect, useState} from 'react';
import {Link} from "react-router-dom";
import {requestURL} from "../../util/common/RequestUrl";
import {useSelector} from "react-redux";

function TrueLogin(props) {

    return (
        <>
            <ul className="nav-item flex">
                <li className="nav-items">
                    <Link to="/login">
                        login
                    </Link>
                </li>
                <li className="nav-items">
                    <Link to="/join-up">
                        join
                    </Link>
                </li>
            </ul>
        </>
    );
}

export default TrueLogin;