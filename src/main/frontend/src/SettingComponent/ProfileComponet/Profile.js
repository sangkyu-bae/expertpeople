import React from 'react';
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function Profile(props) {
    return (
        <div className="container">
            <div className="flex">
                <div className="left">
                    <div className="profile-img-wrap">
                        <FontAwesomeIcon icon={faAddressCard}></FontAwesomeIcon>
                    </div>
                    <div className="itme-wrap">
                        <ul className="my-item">
                            <li className="items">
                                소개
                            </li>
                            <li className="itmes">
                                스터디
                            </li>
                        </ul>
                    </div>
                </div>
                <div className="right">
                    <div className="my-info-wrap">
                        <div className="my-name">이름</div>
                        <div className="my-info">한 줄 소개를 추가하세요.</div>
                    </div>
                    <div className="join-info-wrap">
                        <div className="email">email</div>
                        <div className="join-date">가입날짜</div>
                    </div>
                    <button>프로필 수정</button>
                </div>
            </div>

        </div>
    );
}

export default Profile;
