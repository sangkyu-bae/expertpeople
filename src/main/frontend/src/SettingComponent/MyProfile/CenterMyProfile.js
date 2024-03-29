import React, {useState} from 'react';
import RigthMyProfile from "./RigthMyProfile";

function CenterMyProfile(props) {
    return (
        <>
        <div className="center">
            <form className="profile-form" onSubmit={props.onSubmit}>
                <div className="section"><h1>이름</h1></div>
                <div className="section">
                    <div className="head"> 한 줄 소개</div>
                    <input type="text" name="bio" onChange={props.onChange} value={props.bio}/>
                    <small>길지 않게 35자 이내로 입력하세요</small>
                </div>
                <div className="section">
                    <div className="head"> 자기 이력서 링크</div>
                    <input type="text"/>
                    <small> 자기 자신의 이력을 표현하는 링크를 추가하세요</small>
                </div>
                <div className="section">
                    <div className="head"> 직업</div>
                    <input type="text" name="job" value={props.job} onChange={props.onChange}/>
                    <small>용접공? 목수?</small>
                </div>
                <div className="section">
                    <div className="head"> 활동 지역</div>
                    <input type="text" name="location" value={props.location} onChange={props.onChange}/>
                    <small>주로 일을 하는 지역이나, 가서 일할 지역의 도시 이름을 알려주세요</small>
                </div>
                <button className="update_btn">수정하기</button>
            </form>
        </div>
        </>
    );
}

export default CenterMyProfile;