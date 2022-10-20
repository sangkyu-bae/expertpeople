import React, {useEffect, useState} from 'react';
import './GetRecuritment.css';
import {useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";

function WorkRecruitment(props) {
    const path=useParams();

    const [recruitInfo,setRecruitInfo]=useState([]);
    useEffect(()=>{
        getRecruit();
    },[])
    const getRecruit=()=>{

        axiosCo.getRecruitment(path.path,path.id)
            .then(e=>{
                console.log(e.data);
                setRecruitInfo(e.data);
            })
            .catch(e=>console.log(e));
    }

    return (
        <div className="container work-setting-wrap ">
            <div className="container-wrap nw-co ">
                <div>
                    <div className="recruitment-head mg-bt">
                        <h2><span className='re-name'>test</span> <span> /test</span></h2>
                    </div>
                </div>
                <div className='flex'>
                    <div className='left-flex'>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>상세 채용 설명</span><br/>
                            <span>test</span>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>구직 신청 (0)</span>
                        </div>
                    </div>
                    <div className='right-flex'>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>구인 방법</span><br/>
                            <span>관리자 확인</span>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>모집 인원</span><br/>
                            <span>2명</span>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>채용 마감 일시</span><br/>
                            <span className='mrg-bt-2'>2022년 10월 20일</span><br/>
                            <small>오후 9:47</small>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>계약 기간</span><br/>
                            <span className='mrg-bt-2'>2022년 10월 20일 ~</span><br/>
                            <span>2022년 10월 30일</span>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>담당자</span><br/>
                            <span>규</span>
                        </div>
                        <div className='get-recruit-box'>
                            <span className='recruit-gray'>구인 관리</span><br/>
                            <button className='btn-blue'>구인 수정</button>
                            <button className='btn-red'>구인 취소</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default WorkRecruitment;