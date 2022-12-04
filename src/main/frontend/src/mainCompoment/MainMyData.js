import React, {useEffect} from 'react';
import axiosCo from "../util/common/axiosCommon";

function MainMyData(props) {
    useEffect(()=>{
        getMyData();
    },[])
    const getMyData=()=>{
        axiosCo.getMyData()
            .then(e=>{
                console.log(e.data)
            })
            .catch(e=>{
                console.log(e)
            })
    }
    return (
        <div></div>
    );
}

export default MainMyData;
