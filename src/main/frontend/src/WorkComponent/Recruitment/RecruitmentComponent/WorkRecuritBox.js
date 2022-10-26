import React from 'react';
import {Link, useParams} from "react-router-dom";
import moment from "moment";

function WorkRecruitBox({title,startDateTime,id,numberOfRemainSpot}) {
    startDateTime=moment(startDateTime).format("YYYY-MM-DD  HH:mm");
    const path=useParams();
    return (
        <li className='content-info-item'>
            <div className='content-item-head'>{title}</div>
            <div className='content-item-time'>{startDateTime} 작업 시작</div>
            <div className='content-item-time'>{numberOfRemainSpot} 자리 남음</div>
            <Link to={`/work/${path.path}/recruitment/${id}`}>
                <div className='content-item-detail'>자세히 보기</div>
            </Link>
        </li>
    );
}

export default WorkRecruitBox;