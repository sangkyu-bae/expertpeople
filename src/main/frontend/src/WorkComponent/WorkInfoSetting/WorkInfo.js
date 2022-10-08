import React from 'react';
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import SettingNav from "../WorkCommonComponet/SettingNav";
import {useParams} from "react-router-dom";
import './WorkInfo.css';

function WorkInfo(props) {
    const path=useParams()

    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co">
                <div>
                    <WorkHead></WorkHead>
                    <WorkNav check='setting'></WorkNav>
                </div>

                <div className='flex container-flex'>
                    <SettingNav  path={path}></SettingNav>
                    <div className='work-setting-content'>
                        <div className='work-info-box'>
                            <div className="topic-head">
                                <h2>일감 공개 및 종료</h2>
                            </div>
                            <div className="topic-content locations open-comment">
                                일감을 다른 사용자에게 공개할 준비 되었다면 버튼을 클릭하세요.<br></br>
                                소개,구인직업 및 구인 지역을 등록했는지 확인하세요.<br></br>
                                일감을 공개하면 주요 일하는 지역과 구직 직업에 관심있는 다른 사용자에게 알림을 전송합니다.
                            </div>
                            <button className='job-open-btn'>구인 공개</button>
                        </div>
                        <div className='work-info-box mg-top'>
                            <div className="topic-head">
                                <h2>구인경로</h2>
                            </div>
                            <div className="topic-content">
                               구인 경로를 수정하면 이전에 사용하던 경로를 구인정보에 접근할 수 없으니 주의하세요.
                            </div>
                            <input name="url" type="text"placeholder="예) work-path"/>
                            <button className='job-open-btn yell'>경로 수정</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    );
}

export default WorkInfo;

