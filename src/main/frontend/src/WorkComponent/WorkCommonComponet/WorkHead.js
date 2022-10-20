import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";
import {setWorkInfoReDux} from "../../util/Redux/workReducer";

function WorkHead(props) {
    const dispatch=useDispatch();
    const {path}=useParams();
    const [isSuccess,setIsSuccess]=useState(false);
    useEffect(()=>{
        getWork()
    },[isSuccess])
    const getWork=()=>{
        console.log("?");
        axiosCo.getWork(path)
            .then(e=>{
                //console.log(e.data)
                dispatch(setWorkInfoReDux(e.data));
            })
            .catch(e=>{
                console.log(e)
               props.errors(e)
            });
    }

    const joinMember=()=>{
        axiosCo.joinMember(path)
            .then(e=>{
                setIsSuccess(true);
            })
            .catch(e=>{
                console.log(e);
            })
    }


    const work=useSelector(state=>state.workReducer.work);
    const isManager=useSelector(state=>state.workReducer.isManager);
    const isMember=useSelector(state =>state.workReducer.isMember);
    return (
        <div className="content-wrap">
            <div className="flex">
                <h2 className="work-head">{work.title}</h2>
                {
                    isManager&&
                    <>
                        <div className='togle'>DRAFT</div>
                        <div className='togle'>OFF</div>
                    </>
                }
                {
                    work.published&&!isMember&&
                    <>
                            <button className="join-work-btn" onClick={joinMember}>현장가입</button>
                            <div className="join-count">0</div>

                    </>
                }
            </div>
            <small className="work-head-small">{work.shortDescription}</small>
        </div>
    );
}

export default WorkHead;