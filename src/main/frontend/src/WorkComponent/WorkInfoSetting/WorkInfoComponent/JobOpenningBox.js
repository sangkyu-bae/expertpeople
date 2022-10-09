import React, {useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import {useSelector} from "react-redux";
import OpenningBox from "./JobOpenCp/OpenningBox";
import CloseOpenningBox from "./JobOpenCp/CloseOpenningBox";

function JobOpenningBox(props) {
    const work=useSelector(state=>state.workReducer.work);
    const [isRecruiting,setIsRecruiting]=useState(work.recruiting);
    const [isRecruitingSuccess,SetIsRecruitingSuccess]=useState(false);
    const [isStopRecruitingSuccess,SetIsStopRecruitingSuccess]=useState(false);
    const [isStopError,setIsStopError]=useState(false);

    const jobOpen=()=>{
        axiosCo.openRecruit(props.path.path)
            .then(e=>{
                setIsRecruiting(true);
                SetIsRecruitingSuccess(true);
            })
            .catch(e=>console.log(e))
    }

    const stopRecruit=()=>{
        axiosCo.stopRecruit(props.path.path)
            .then(e=>{
                SetIsStopRecruitingSuccess(true);
            })
            .catch(e=>setIsStopError(true))

    }

    return (
        <>
            {
                isRecruitingSuccess&&
                <div className="topic-content locations open-comment">
                    구인을 시작 했습니다.
                </div>
            }
            {
                isStopRecruitingSuccess&&
                <div className="topic-content locations open-comment al">
                    구인을 종료 했습니다.
                </div>
            }
            {
                isStopError&&
                <div className="topic-content locations open-comment al">
                    1시간이 지나지 않았습니다. 1시간이 지난후 다시 요청하세요.
                </div>
            }
            <div className="topic-head">
                <h2>구인</h2>
            </div>
            {
                !isRecruiting?
                <OpenningBox jobOpen={jobOpen}></OpenningBox>:
                <CloseOpenningBox stopRecruit={stopRecruit}></CloseOpenningBox>
            }
        </>
    );
}

export default JobOpenningBox;