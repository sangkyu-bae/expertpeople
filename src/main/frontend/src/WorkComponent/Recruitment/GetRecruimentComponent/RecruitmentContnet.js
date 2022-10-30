import React from 'react';
import {Link, useParams} from "react-router-dom";
import ContentTable from "./ContentTable";

function RecruitmentContnet({recruitment, isManager, enrollments}) {
    const path = useParams();
    return (
        <div className='flex'>
            <div className='left-flex'>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>상세 채용 설명</span><br/>
                    <span dangerouslySetInnerHTML={{__html: recruitment.description}}></span>
                </div>
                <div>
                    <div className='get-recruit-box'>
                    <span className='recruit-gray'>구직 신청 ({
                        enrollments.length
                    })</span>
                    </div>
                    <div className='job-huntin-box'>
                       <ContentTable enrollments={enrollments}></ContentTable>
                    </div>
                </div>
            </div>
            <div className='right-flex'>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>구인 방법</span><br/>
                    {
                        recruitment.eventType == 'FCFS' ?
                            <span>선착순</span> :
                            <span>관리자 확인</span>
                    }
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>모집 인원</span><br/>
                    <span>{recruitment.limitOfEnrollments}명</span>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>채용 마감 일시</span><br/>
                    <span className='mrg-bt-2'>{recruitment.endEnrollmentDateTime}</span><br/>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>계약 기간</span><br/>
                    <span className='mrg-bt-2'>{recruitment.startDateTime} ~</span><br/>
                    <span>{recruitment.endDateTime}</span>
                </div>
                <div className='get-recruit-box'>
                    <span className='recruit-gray'>담당자</span><br/>
                    <span>{recruitment.createName}</span>
                </div>
                {
                    isManager &&
                    <div className='get-recruit-box'>
                        <span className='recruit-gray'>구인 관리</span><br/>
                        <Link to={`/work/${path.path}/update-recruitment/${path.id}`}>
                            <button className='btn-blue'>구인 수정</button>
                        </Link>
                        <button className='btn-red'>구인 취소</button>
                    </div>
                }
            </div>
        </div>
    );
}

export default RecruitmentContnet;