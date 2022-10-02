import React from 'react';

function WorkNav(props) {
    return (
        <div>
            <ul className="work-info">
                <li className={`work-info-nav ${props.check=='info'&& "work-click"}`}>소개</li>
                <li className="work-info-nav">구성원</li>
                <li className="work-info-nav">모임</li>
                <li className="work-info-nav">설정</li>
            </ul>
        </div>
    );
}

export default WorkNav;