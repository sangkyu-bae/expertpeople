import React from 'react';

function OpenningBox(props) {
    return (
        <>
            <div className="topic-content locations open-comment">
                구인을 시작합니다.<br></br>
                충분한 필요 인력을풀을 형성 했다면 모집을 멈출 수 있습니다.<br></br>
                구인 정보는 1시간에 한번만 바꿀 수 있습니다.
            </div>
            <button className='job-open-btn' onClick={props.jobOpen}>구인 시작</button>
        </>

    );
}

export default OpenningBox;