import React from 'react';
import {useSelector} from "react-redux";
import {Link, useParams} from "react-router-dom";

function WorkNav(props) {
    const isManager=useSelector(state=>state.workReducer.isManager);
    const {path}=useParams();
    return (
        <div>
            <ul className="work-info">
                <Link to={`/work/${path}`}>
                    <li className={`work-info-nav ${props.check=='info'&& "work-click"}`}>소개</li>
                </Link>
                <Link to={`/work/${path}/members`}>
                    <li className={`work-info-nav ${props.check=='members'&& "work-click"}`}>구성원</li>
                </Link>

                <li className="work-info-nav">모임</li>
                {
                    isManager&&
                        <Link to={`/work/${path}/setting`}>
                            <li className={`work-info-nav ${props.check=='setting'&& "work-click"}`}>설정</li>
                        </Link>
                }

            </ul>
        </div>
    );
}

export default WorkNav;