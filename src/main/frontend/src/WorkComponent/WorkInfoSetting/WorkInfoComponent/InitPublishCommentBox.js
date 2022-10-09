import React from 'react';

function InitPublishCommentBox(props) {
    return (
        <>
            <div className="topic-content locations open-comment">
                일감을 다른 사용자에게 공개할 준비 되었다면 버튼을 클릭하세요.<br></br>
                소개,구인직업 및 구인 지역을 등록했는지 확인하세요.<br></br>
                일감을 공개하면 주요 일하는 지역과 구직 직업에 관심있는 다른 사용자에게 알림을 전송합니다.
            </div>
            <button className='job-open-btn' onClick={props.openWork}>현장 공개</button>
        </>
    );
}

export default InitPublishCommentBox;