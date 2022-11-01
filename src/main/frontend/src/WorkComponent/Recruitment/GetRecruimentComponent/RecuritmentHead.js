import React, {useEffect, useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {setAddEnrollmentInfo} from "../../../util/Redux/enrollmentRedcuer";

function RecuritmentHead({title,workTitle}) {
    const path=useParams();
    const isEnrollment=useSelector(state=>state.enrollmentReducer.isEnrollment);
    const dispatch=useDispatch();
    const addEnrollment=()=>{
        axiosCo.addEnrollment(path.path,path.id)
            .then(e=>{
                dispatch(setAddEnrollmentInfo(e.data,true))
            })
            .catch(e=>console.log(e));
    }
    return (
        <div className='flexs'>
            <div className="recruitment-head mg-bt left-flex">
                <h2><span className='re-name'>{workTitle}</span> <span>/{title}</span></h2>
            </div>
            {
                !isEnrollment &&
                <div className='right-flex'>
                    <button className='blue-btns' onClick={addEnrollment}>참가신청</button>
                </div>
            }

        </div>
    );
}

export default RecuritmentHead;