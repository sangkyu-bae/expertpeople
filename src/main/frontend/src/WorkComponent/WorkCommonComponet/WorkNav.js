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

                <Link to={`/work/${path}/recruitment`}>
                    <li className={`work-info-nav ${props.check=='recruit'&& "work-click"}`}>구인인력</li>
                </Link>
                {
                    isManager&&
                        <>
                            <Link to={`/work/${path}/setting/info`}>
                                <li className={`work-info-nav ${props.check=='setting'&& "work-click"}`}>설정</li>
                            </Link>
                        </>
                }

            </ul>
        </div>
    );
}

export default WorkNav;