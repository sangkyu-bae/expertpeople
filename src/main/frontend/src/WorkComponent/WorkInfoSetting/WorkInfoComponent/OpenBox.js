import React, {useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";
import InitPublishCommentBox from "./InitPublishCommentBox";
import {useSelector} from "react-redux";
import PublishWorkCommentBox from "./PublishWorkCommentBox";

function OpenBox(props) {
    const work=useSelector(state=>state.workReducer.work);
    const [isPublish,setIsPublish]=useState(work.published);
    const [isPublishSuccess,setIsPublishSuccess]=useState(false);
    const openWork=()=>{
        axiosCo.updatePublish(props.path.path)
            .then(e=>{
                setIsPublish(true);
                setIsPublishSuccess(true);
            })
            .catch(e=>console.log(e) )
    }
    return (
        <>
            {
                isPublishSuccess&&
                <div className="topic-content locations open-comment">
                    일감을 공개했습니다.
                </div>
            }
            <div className="topic-head">
                <h2>일감 현장 공개 및 종료</h2>
            </div>
            {
                !isPublish?
                <InitPublishCommentBox openWork={openWork}></InitPublishCommentBox>:
                <PublishWorkCommentBox></PublishWorkCommentBox>
            }
        </>
    );
}

export default OpenBox;