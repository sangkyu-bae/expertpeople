import React, {useEffect, useRef, useState} from 'react';
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import './RecruitmentList.css';
import axiosCo from "../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import WorkRecruitBox from "./RecruitmentComponent/WorkRecuritBox";
function RecruitmentList(props) {
    const path=useParams();
    const [recruitments,setRecruitments]=useState({
        oldRecruitments:[],
        newRecruitments:[]
    });

    const {oldRecruitments,newRecruitments}=recruitments;
    const [location,setLocation]=useState('old');

    const changeLocation=(e)=>{
        const classList=e.target.className;
        if(classList.indexOf('check')<0) {
            document.querySelector('.check').classList.remove('check');
            e.target.classList.add('check')

            if(classList.indexOf('old')>0)setLocation('old');
            else setLocation('new');
        }
    }
    const getRecruitments=()=>{
        axiosCo.getWorkInRecruitments(path.path)
            .then(e=>{
                console.log(e.data);
                setRecruitments({
                    oldRecruitments: e.data.oldRecruitments,
                    newRecruitments: e.data.newRecruitments
                })
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
                            <li className='info-item firs new' onClick={changeLocation}>새 구인</li>
                            <li className='info-item old check' onClick={changeLocation}>지난 구인</li>
                        </ul>
                    </div>
                    <div className='work-info-content work-info-right'>
                        <ul className='work-flex work-flex-list'>
                            {
                                location=='old'?
                                oldRecruitments.map(e=><WorkRecruitBox title={e.title} startDateTime={e.startDateTime} key={e.id} id={e.id} numberOfRemainSpot={e.numberOfRemainSpot}></WorkRecruitBox>):
                                    newRecruitments.map(e=><WorkRecruitBox title={e.title} startDateTime={e.startDateTime} key={e.id}></WorkRecruitBox>)
                            }
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default RecruitmentList;