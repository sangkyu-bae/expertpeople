import React, {useEffect, useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import axiosCo from "../../util/common/axiosCommon";
import {setWorkInfoReDux} from "../../util/Redux/workReducer";

function WorkHead(props) {
    const dispatch=useDispatch();
    const {path}=useParams();

    useEffect(()=>{
         getWork()
    },[])

    const getWork=()=>{
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

    const work=useSelector(state=>state.workReducer.work);
    return (
        <div className="content-wrap">
            <div className="flex">
                <h2 className="work-head">{work.title}</h2>
                <div className='togle'>DRAFT</div>
                <div className='togle'>OFF</div>
            </div>
            <div>

            </div>

            <small className="work-head-small">{work.shortDescription}</small>
        </div>
    );
}

export default WorkHead;