import React, {useEffect, useState} from 'react';
import './Work.css';
import WorkNav from "./WorkNav";
import axios from "axios";
import axiosCo from "../../util/common/axiosCommon";
import {useLocation, useParams} from "react-router-dom";
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "./WorkHead";
function Work(props) {

    const [workInfo,setWorkInfo]=useState();
    const {path}=useParams();
    const [error,setError]=useState({
        isError:false,
        errorMessage:""
    });
    const {isError,errorMessage}=error;

    useEffect(()=>{
        getWork()
    },[])
    const getWork=()=>{
        axiosCo.getWork(path)
            .then(e=>{
                console.log(e.data)
                setWorkInfo(e.data);
            })
            .catch(e=>{
                setError({
                    ...error,
                    isError:true,
                    errorMessage:e.response.data.message
                })
            });
    }

    return (
        <div className="container ">

            <div className="container-wrap nw-co">
                {
                    isError&&<NewWorkAlert text={errorMessage}></NewWorkAlert>
                }
                {
                    !isError&& workInfo&&
                    <>
                        <WorkHead workInfo={workInfo}></WorkHead>
                        <WorkNav workManager={workInfo} check='info'></WorkNav>
                    </>
                }
            </div>
        </div>
    );
}

export default Work;
