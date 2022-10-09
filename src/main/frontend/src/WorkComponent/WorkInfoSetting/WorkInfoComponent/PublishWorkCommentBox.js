import React from 'react';

function PublishWorkCommentBox(props) {
    return (
        <>
            <div className="topic-content topic-m">
                구인을 마치셨다면 구인을 종료하세요.<br></br>
                구인을 종료하면 더이상 사람을 모집하거나, 사전일정 모임을 만들 수 없으며, 구인 경로와 이름을 수정할 수 없습니다.<br></br>
                구직 된 인원 기록은 그대로 보관됩니다.
            </div>
            <button className='job-open-btn yell'>현장 공개 종료</button>
        </>
    );
}

export default PublishWorkCommentBox;