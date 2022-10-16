import React, {useEffect, useState} from 'react';
import './Recruitment.css';
import axiosCo from "../../util/common/axiosCommon";
import RecuritmentGropBox from "./RecruitmentComponent/RecuritmentGropBox";
import {useParams} from "react-router-dom";

function NewRecruitment(props) {
    const path=useParams();
    const [inputs,setInputs]=useState({
        title:'',
        description:'',
        endEnrollmentDateTime:'',
        startDateTime:'',
        endDateTime:'',
        jobName:'일반(일반)',
        limitOfEnrollments:'',
        eventType:'FCFS'
    })
    const onChange=e=>{
        //console.log(e.target);
        const{value,name}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        })
    }
    const onSubmit=e=>{
        e.preventDefault();
        addRecruit();
        console.log(inputs);
    }
    const addRecruit=()=>{
        axiosCo.addRecruitment(inputs,path.path)
            .then(e=>console.log(e.data))
            .catch(e=>console.log(e));
    }
    const{title,description,endEnrollmentDateTime,startDateTime,endDateTime,jobName,limitOfEnrollments,eventType}=inputs;
    return (
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co new-recruitment-boxs">
                <div className="new-recruitment-head mg-bt">
                    <h2><span className='re-name'>테스트 채용</span> <span> /새 구인 만들기</span></h2>
                </div>
                <form onSubmit={onSubmit}>
                    <div className='new-recruitment-content re-name-head mg-bt'>
                        <div className='re-head'>채용 제목</div>
                        <input type='text' value={title} name='title' onChange={onChange} placeholder='채용 제목'/>
                    </div>
                    <RecuritmentGropBox onChange={onChange}></RecuritmentGropBox>
                    <div className='new-recruitment-content mg-bt'>
                        <div className='re-head'>모집 방법</div>
                        <select name="eventType" onChange={onChange}>
                            <option value="FCFS" >선착순</option>
                            <option value="COMFIRMATIVE">관리자 확인</option>
                        </select>
                    </div>
                    <div className='new-recruitment-content mg-bt flex'>
                        <div className='new-recruitment-box'>
                            <div className='re-head'>모집 인원</div>
                            <input type='number' name='limitOfEnrollments' value={limitOfEnrollments}  onChange={onChange}/>
                        </div>
                        <div className='new-recruitment-box'>
                            <div className='re-head'>등록 마감 일시</div>
                            <input type='date' name='endEnrollmentDateTime' value={endEnrollmentDateTime}  onChange={onChange}/>
                        </div>
                        <div className='new-recruitment-box'>
                            <div className='re-head'>일 시작 시간</div>
                            <input type='date'  name='startDateTime' value={startDateTime}  onChange={onChange}/>
                        </div>
                        <div className='new-recruitment-box'>
                            <div className='re-head'>일 종료 시간</div>
                            <input type='date' name='endDateTime' value={endDateTime}  onChange={onChange}/>
                        </div>
                    </div>
                    <div className='re-head'>
                        <div>구인 설명</div>
                        <textarea name='description' value={description} onChange={onChange}></textarea>
                    </div>
                    <button>등록하기</button>
                </form>

            </div>
        </div>
    );
}

export default NewRecruitment;