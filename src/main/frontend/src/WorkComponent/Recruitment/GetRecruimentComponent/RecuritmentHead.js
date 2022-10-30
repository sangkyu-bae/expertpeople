import React, {useEffect} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import {useParams} from "react-router-dom";

function RecuritmentHead({title,workTitle,isEnrollment,addRecruitInfoEnrollment}) {
    const path=useParams();
    console.log(isEnrollment)
    const addEnrollment=()=>{
        axiosCo.addEnrollment(path.path,path.id)
            .then(e=>{
                console.log(e.data);
                addRecruitInfoEnrollment(e.data);
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