import React, {useEffect} from 'react';
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import './RecruitmentList.css';
import axiosCo from "../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
function RecruitmentList(props) {
    const path=useParams();
    const getRecruitments=()=>{
        axiosCo.getWorkInRecruitments(path.path)
            .then(e=>{
                console.log(e.data);
            })
            .catch(e=>{
                console.log(e);
            })
    }

    useEffect(()=>{
       getRecruitments()
    },[])
    return (
        <div className="container ">
            <div className="container-wrap nw-co">
                        <WorkHead></WorkHead>
                        <WorkNav check='recruit'></WorkNav>
                <div className='work-flex'>
                    <div className='info-list work-info-left'>
                        <ul className='info-box'>
                            <li className='info-item firs'>새 구인</li>
                            <li className='info-item'>지난 구인</li>
                        </ul>
                    </div>
                    <div className='work-info-content work-info-right'>
                        <ul className='content-info-box work-flex'>
                            <li className='content-info-item'>
                                <div className='content-item-head'>테스트</div>
                                <div className='content-item-time'>일요일 오전 09시 일 시작</div>
                                <div className='content-item-detail'>자세히 보기</div>
                            </li>
                            <li className='content-info-item'>
                                <div className='content-item-head'>테스트</div>
                                <div className='content-item-time'>일요일 오전 09시 일 시작</div>
                                <div className='content-item-detail'>자세히 보기</div>
                            </li>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default RecruitmentList;