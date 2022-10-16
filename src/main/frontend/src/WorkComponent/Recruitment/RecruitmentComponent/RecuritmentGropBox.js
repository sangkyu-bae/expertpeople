import React, {useEffect, useState} from 'react';
import axiosCo from "../../../util/common/axiosCommon";

function RecuritmentGropBox(props) {
    useEffect(()=>{
        getJobs();
    },[])
    const [jobs,setJobs]=useState([]);

    const getJobs=()=>{
        axiosCo.getJobs()
            .then(e=>{
                console.log(e.data);
                setJobs(e.data);
            })
            .catch(e=>{
                console.log(e);
            })
    }
    return (
        <div className='new-recruitment-content mg-bt'>
            <div className='re-head'>구인 직군</div>
            <select name="jobName" onChange={props.onChange}>
                {
                    jobs&&jobs.length>0 &&
                    jobs.map((job)=>
                        <>
                            <option value={job}>{job}</option>
                        </>
                    )
                }
            </select>
        </div>
    );
}

export default RecuritmentGropBox;