import React, {useEffect} from 'react';
import axiosCo from "../util/common/axiosCommon";

function Notification(props) {
    useEffect(()=>{
        getNotify()
    },[])
    const getNotify=()=>{
        axiosCo.getNotification()
            .then(e=>{
                console.log(e.data);
            })
            .catch(e=>{
                console.log(e);
            })
    }
    return (
        <div></div>
    );
}

export default Notification;
