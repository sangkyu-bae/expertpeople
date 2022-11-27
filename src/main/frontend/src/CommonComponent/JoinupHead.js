import React from 'react';
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";

function JoinupHead(props) {
    return (
        <div className="py-5 text-center">
            <h2> 전문인력들을 만나다 ExpertPeople</h2>
            <div className="head-content">
                <small>태그와 지역 기반으로 일감을 찾고 구인,구직에 참여하세요</small><br/>
                <small>일감별 구인 관리 기능을 제공합니다.</small>
            </div>
            <Link to="/join-up">
                <Button className="join-btn" variant="contained">회원가입</Button>
            </Link>
        </div>
    );
}

export default JoinupHead;
