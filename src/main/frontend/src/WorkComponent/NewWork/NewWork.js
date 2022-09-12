import React from 'react';
import LeftMyProfile from "../../SettingComponent/MyProfile/LeftMyProfile";
import CenterAttention from "../../SettingComponent/AttentionTopic/CenterAttention";
import './NewWork.css';
function NewWork(props) {
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="content-wrap">
                    <div className="work-head-wrap">
                        <h1>일감 개설</h1>
                    </div>
                    <form>
                        <div className="work-head-content">
                            <div className="work-content">
                                <h2>일감 URL</h2>
                                <input type="text"placeholder="예) work-path"/>
                            </div>
                            <div className="work-content">
                                <h2>일감 이름</h2>
                                <input type="text" placeholder="일감 이름"/>
                            </div>
                            <div className="work-content short">
                                <h2>짧은 소개</h2>
                                <input type="text" placeholder="일감을 짧게 소개해 주세요"/>
                            </div>
                            <div className="work-content">
                                <h2>상세 소개</h2>
                            </div>
                            <button className='password-btn new-study-btn'>스터디 만들기</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default NewWork;