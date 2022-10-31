import React, {useEffect, useState} from 'react';
import './GetRecuritment.css';
import {useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";
import moment from "moment";
import RecuritmentHead from "./GetRecruimentComponent/RecuritmentHead";
import RecruitmentContnet from "./GetRecruimentComponent/RecruitmentContnet";
import {useDispatch, useSelector} from "react-redux";
import enrollmentReducer, {setAddEnrollmentInfo, setEnrollmentInfoRedux} from "../../util/Redux/enrollmentRedcuer";


function WorkRecruitment(props) {
    const path=useParams();

    const dispatch=useDispatch();
    const enrollment=useSelector(state => state.enrollmentReducer.enrollments);
    const isEnrollments=useSelector(state=>state.enrollmentReducer.isEnrollment)
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
    // console.log(enrollment);
    // console.log(isEnrollments)
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
                dispatch(setEnrollmentInfoRedux(e.data.recruitment.erollments,e.data.isMember));
            })
            .catch(e=>console.log(e));
    }
    // console.log(recruitment)
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