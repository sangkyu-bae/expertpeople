import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCalendarCheck, faEnvelope, faLocationArrow, faPersonDigging} from "@fortawesome/free-solid-svg-icons";

function ProfieRigth(props) {
    return (
        <div className="right">
            <div className="my-info-wrap">
                <div className="my-name">이름</div>
                <div className="my-info">한 줄 소개를 추가하세요.</div>
            </div>
            <div className="join-info-wrap">
                <div className="job">
                    <FontAwesomeIcon className="icons" icon={faPersonDigging}></FontAwesomeIcon>
                    <span>업종</span>
                </div>
                <div className="location">
                    <FontAwesomeIcon className="icons" icon={faLocationArrow}></FontAwesomeIcon>
                    <span>지역</span>
                </div>
                <div className="email">
                    <FontAwesomeIcon className="icons" icon={faEnvelope}></FontAwesomeIcon>
                    <span>email</span>
                </div>
                <div className="join-date">
                    <FontAwesomeIcon className="icons" icon={faCalendarCheck}></FontAwesomeIcon>
                    <span>경력</span>
                </div>
            </div>
            <button>프로필 수정</button>
        </div>
    );
}

export default ProfieRigth;