import React from 'react';
import {useSelector} from "react-redux";

function WorkNav(props) {
    const isManager=useSelector(state=>state.workReducer.isManager);

    return (
        <div>
            <ul className="work-info">
                <li className={`work-info-nav ${props.check=='info'&& "work-click"}`}>소개</li>
                <li className={`work-info-nav ${props.check=='members'&& "work-click"}`}>구성원</li>
                <li className="work-info-nav">모임</li>
                {
                    isManager&&<li className="work-info-nav">설정</li>
                }

            </ul>
        </div>
    );
}

export default WorkNav;