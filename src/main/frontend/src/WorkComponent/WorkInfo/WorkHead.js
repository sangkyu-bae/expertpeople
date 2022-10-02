import React, {useEffect, useState} from 'react';

function WorkHead(props) {

    const [workInfo,setWorkInfo]=useState();
    useEffect(()=>{
        setWorkInfo(props.workInfo.work);
    },[])

    return (
        <>
        {
            workInfo&&
            <div className="content-wrap">
                <h2 className="work-head">{workInfo.title}</h2>
                <small className="work-head-small">{workInfo.shortDescription}</small>
            </div>
        }
        </>
    );
}

export default WorkHead;