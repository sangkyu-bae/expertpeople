import React from 'react';

function CloseOpenningBox(props) {
    return (
        <>
            <div className="topic-content locations open-comment">
                구인을 종료합니다.<br></br>
                인력이 필요 하다면 모집을 시작할 수 있습니다.<br></br>
                구인 정보는 1시간에 한번만 바꿀 수 있습니다.
            </div>
            <button className='job-open-btn' onClick={props.stopRecruit}>구인 종료</button>
        </>
    );
}

export default CloseOpenningBox;