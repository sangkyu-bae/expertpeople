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
        recruitment:{}
    }]);
    const {isManager,recruitment}=recruitInfo;

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
                    recruitment:e.data.recruitment
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
                        <RecuritmentHead title={recruitment.work.title} title={recruitment.title} ></RecuritmentHead>
                        <RecruitmentContnet recruitment={recruitment} isManager={isManager}></RecruitmentContnet>
                    </>
                }
            </div>
        </div>
    );
}

export default WorkRecruitment;