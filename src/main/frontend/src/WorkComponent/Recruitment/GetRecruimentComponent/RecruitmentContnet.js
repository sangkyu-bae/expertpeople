import React from 'react';

function RecruitmentContnet(props) {
    return (
        <div className='flex'>
            <div className='left-flex'>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>상세 채용 설명</span><br/>
                    <span>{props.recruitment.description}</span>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>구직 신청 (0)</span>
                </div>
            </div>
            <div className='right-flex'>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>구인 방법</span><br/>
                    {
                        props.recruitment.eventType=='FCFS'?
                            <span>선착순</span>:
                            <span>관리자 확인</span>
                    }
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>모집 인원</span><br/>
                    <span>{props.recruitment.limitOfEnrollments}명</span>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>채용 마감 일시</span><br/>
                    <span className='mrg-bt-2'>{props.recruitment.endEnrollmentDateTime}</span><br/>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>계약 기간</span><br/>
                    <span className='mrg-bt-2'>{props.recruitment.startDateTime} ~</span><br/>
                    <span>{props.recruitment.endDateTime}</span>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>담당자</span><br/>
                    <span>{props.recruitment.createBy.name}</span>
                </div>
                {
                    props.isManager&&
                    <div className='get-recruit-box'>
                        <span className='recruit-gray'>구인 관리</span><br/>
                        <button className='btn-blue'>구인 수정</button>
                        <button className='btn-red'>구인 취소</button>
                    </div>
                }
            </div>
        </div>
    );
}

export default RecruitmentContnet;