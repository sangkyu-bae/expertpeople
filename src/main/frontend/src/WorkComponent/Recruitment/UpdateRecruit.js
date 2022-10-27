import React, {useEffect, useState} from 'react';
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import axiosCo from "../../util/common/axiosCommon";
import {useNavigate, useParams} from "react-router-dom";
import formValidation from "../../util/common/Validation";

function UpdateRecruit(props) {
    const path=useParams();
    const nav=useNavigate();
    const [inputs,setInputs]=useState({
        title:"",
        eventType:"",
        limitOfEnrollments:"",
        endEnrollmentDateTime:"",
        startDateTime:"",
        endDateTime:"",
        description:""
    })
    const {title,eventType,limitOfEnrollments,endEnrollmentDateTime,startDateTime,endDateTime}=inputs;
    const onChange=(e)=>{
        const{value,name}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        })
    }
    const [recruitInfo,setRecruitInfo]=useState([])
    const [error,setError]=useState({
        isManagerError:false,
        isServerError:false,
        isFormCheckError:false
    })
    useEffect(()=>{
        getRecruit();
    },[])
    const getRecruit=()=>{
        axiosCo.getRecruitment(path.path,path.id)
            .then(e=>{
                if(!e.data.isManager){
                    setError({
                        ...error,
                        isManagerError: true
                    })
                }else{
                    console.log(e.data);
                    setRecruitInfo(e.data.recruitment)
                }
            })
            .catch(e=>{
                console.log(e);
                setError({
                    ...error,
                    isServerError: true
                })
            })
    }
    const onSubmit=(e)=>{
        e.preventDefault();
        const isCheck=formValidation.updateRecruit(inputs);
        if(!isCheck) {
            setError({
                ...Error,
                isFormCheckError:true
            })
            return false;
        }
        axiosCo.updateRecruit(path.path,path.id)
            .then(e=>{
                nav(`/work/${path.path}/recruitment/${path.id}`)
            })
            .catch(e=>{
                console.log(e);
                setError({
                    ...error,
                    isServerError: true
                })
            })
    }
    return (
        <div className="container work-setting-wrap new-re-wrap">
            <div className="container-wrap nw-co new-recruitment-boxs">
                {
                    recruitInfo&&
                    <>
                        <div className="new-recruitment-head mg-bt">
                            <h2><span className='re-name'>{recruitInfo.title}</span> <span> /{recruitInfo.workTitle}</span></h2>
                        </div>
                        <form onSubmit={onSubmit}>
                            <div className='new-recruitment-content re-name-head mg-bt'>
                                <div className='re-head'>채용 제목</div>
                                <input type='text'  name='title' placeholder='채용 제목' value={title} onChange={onChange}/>
                                <small>채용 제목을 50자 이내로 입력하세요.</small>
                            </div>
                            <div className='new-recruitment-content mg-bt'>
                                <div className='re-head'>모집 방법</div>
                                <select name="eventType" value={eventType} onChange={onChange}>
                                    <option value="FCFS" >선착순</option>
                                    <option value="COMFIRMATIVE">관리자 확인</option>
                                </select>
                                <small>
                                    두가지 모집 방법이 있습니다.<br/>
                                    선착순으로 모집하는 경우, 모집 인원 이내의 접수는 자동으로 확정되며, 채용 인원을 넘는 신청은 대기 신청이 되어 확정된 신청 중 취소가 발생하면 선착순으로 대기 구직자를 확정 변경 합니다. 단, 등록 마감일 이후에는 취소하여도 확정 여부가 바뀌지 않습니다.<br/>
                                    확인으로 모집하는 경우, 구직 신청자을 확인하여 확정 여부를 확인 할 수 있습니다.
                                </small>
                            </div>
                            <div className='new-recruitment-content mg-bt flex'>
                                <div className='new-recruitment-box'>
                                    <div className='re-head'>모집 인원</div>
                                    <input type='number' name='limitOfEnrollments' value={limitOfEnrollments} onChange={onChange}/>
                                </div>
                                <div className='new-recruitment-box'>
                                    <div className='re-head'>등록 마감 일시</div>
                                    <input type='date' name='endEnrollmentDateTime' value={endEnrollmentDateTime} onChange={onChange}/>
                                </div>
                                <div className='new-recruitment-box'>
                                    <div className='re-head'>일 시작 시간</div>
                                    <input type='date'  name='startDateTime' value={startDateTime} onChange={onChange}/>
                                </div>
                                <div className='new-recruitment-box'>
                                    <div className='re-head'>일 종료 시간</div>
                                    <input type='date' name='endDateTime' value={endDateTime} onChange={onChange}/>
                                </div>
                            </div>
                            <div className='re-head'>
                                <div className='mg-b2'>구인 설명</div>
                                <CKEditor
                                    editor={ ClassicEditor }
                                    data=""
                                    onReady={ editor => {
                                        // You can store the "editor" and use when it is needed.
                                        console.log( 'Editor is ready to use!', editor );
                                    } }
                                    onChange={ ( event, editor ) => {
                                        const data = editor.getData();
                                        setInputs({
                                            ...inputs,
                                            description: data
                                        })
                                        console.log( { event, editor, data } );
                                    } }
                                />
                            </div>
                            <button>등록하기</button>
                        </form>
                    </>
                }


            </div>
        </div>
    );
}

export default UpdateRecruit;