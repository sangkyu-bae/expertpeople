import React from 'react';
import ProfileLeft from "../SettingCommon/ProfileLeft";
import ProfieRigth from "../SettingCommon/ProfieRigth";
import "./MyProfile.css";
import LeftMyProfile from "./LeftMyProfile";
function MyProfile(props) {
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <LeftMyProfile></LeftMyProfile>
                  
                    <div className="center">
                        <form className="profile-form">
                            <div className="section"> <h1>이름</h1></div>
                            <div className="section">
                                <div className="head"> 한 줄 소개</div>
                                <input type="text"/>
                                <small>길지 않게 35자 이내로 입력하세요</small>
                            </div>
                            <div className="section">
                                <div className="head"> 자기 이력서 링크</div>
                                <input type="text"/>
                                <small> 자기 자신의 이력을 표현하는 링크를 추가하세요</small>
                            </div>
                            <div className="section">
                                <div className="head"> 직업</div>
                                <input type="text"/>
                                <small>용접공? 목수?</small>
                            </div>
                            <div className="section">
                                <div className="head"> 활동 지역</div>
                                <input type="text"/>
                                <small>주로 일을 하는 지역이나, 가서 일할 지역의 도시 이름을 알려주세요</small>
                            </div>
                            <button>수정하기</button>
                        </form>
                     
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MyProfile;