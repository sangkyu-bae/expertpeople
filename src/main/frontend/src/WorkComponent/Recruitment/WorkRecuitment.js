import React, {useEffect, useState} from 'react';
import './GetRecuritment.css';
import {useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";
import moment from "moment";
import RecuritmentHead from "./GetRecruimentComponent/RecuritmentHead";
import RecruitmentContnet from "./GetRecruimentComponent/RecruitmentContnet";

function WorkRecruitment(props) {
    const path=useParams();

    const [recruitInfo,setRecruitInfo]=useState([{
        isManager:false,
        isEnrollment:false,
        recruitment:{},
        enrollments:[]
    }]);
    const {isManager,recruitment,isEnrollment,enrollments}=recruitInfo;
    const addRecruitInfoEnrollment=(data)=>{
        setRecruitInfo({
            ...recruitInfo,
           isEnrollment:true,
            enrollments:data.erollments
        })
    }
    useEffect(()=>{
        getRecruit();
    },[])

    const dateForm='YYYY-MM-DD';
    const getRecruit=()=>{
        axiosCo.getRecruitment(path.path,path.id)
            .then(e=>{
                e.data.recruitment.endDateTime=moment().format(dateForm);
                e.data.recruitment.startDateTime=moment().format(dateForm);
                e.data.recruitment.endEnrollmentDateTime=moment().format(dateForm)
                setRecruitInfo({
                    isManager:e.data.isManager,
                    isEnrollment:e.data.isMember,
                    recruitment:e.data.recruitment,
                    enrollments:e.data.recruitment.erollments
                });
            })
            .catch(e=>console.log(e));
    }
    console.log(recruitment)
    return (
        <div className="container work-setting-wrap ">
            <div className="container-wrap nw-co ">
                {
                    recruitment&&
                    <>
                        <RecuritmentHead
                            workTitle={recruitment.workTitle}
                            title={recruitment.title}
                            isEnrollment={isEnrollment}
                            addRecruitInfoEnrollment={addRecruitInfoEnrollment}
                        ></RecuritmentHead>
                        <RecruitmentContnet recruitment={recruitment} isManager={isManager} enrollments={enrollments}></RecruitmentContnet>
                    </>
                }
            </div>
        </div>
    );
}

export default WorkRecruitment;