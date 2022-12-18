import React, {useEffect, useState} from 'react';
import moment from "moment";
import {Link} from "react-router-dom";
import Notice from "../../util/Class/Notice";

function NoticeItemBox({notification,readNotification}) {

    const [diffTime,setDiffTimes]=useState('');

    useEffect(()=>{
        const notice=new Notice();
        const time=notice.getDiffTime(notification.createDateTime)
        setDiffTimes(time);
    },[])

    return (
        <>
        {
            diffTime!=''&&
            <li className='noti-item' onClick={()=>readNotification(notification.id,notification.link)}>
                <span className="noti-title">{notification.title}</span>
                <span className='item-cnt'>{diffTime}</span>
            </li>
        }
        </>

    );
}

export default NoticeItemBox;
