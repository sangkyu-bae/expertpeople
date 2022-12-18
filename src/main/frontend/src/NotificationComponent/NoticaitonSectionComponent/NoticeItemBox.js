import React, {useEffect, useState} from 'react';
import moment from "moment";
import {Link} from "react-router-dom";

function NoticeItemBox({notification,readNotification}) {

    const [diffTime,setDiffTimes]=useState('');
    const getDiffTime =(nowDate, createDate)=> {
        let time;
        const day = nowDate.diff(createDate, 'days')
        const hours = nowDate.diff(createDate, 'hours')
        const minute = nowDate.diff(createDate, 'minute')
        if (day > 0) {
            let checkHours = hours;
            while (checkHours > 24) {
                checkHours = checkHours - 24;
            }
            time = `${day}일 ${checkHours}시간`
        }

        if (hours > 0) {
            let checkMinute = minute;
            while (checkMinute > 60) {
                checkMinute = checkMinute - 60;
            }
            time += ` ${checkMinute}분전`
        }

        setDiffTimes(time);
    }

    useEffect(()=>{
        const createDate=moment(notification.createDateTime);
        const nowDate=moment();
        getDiffTime(nowDate, createDate);
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
