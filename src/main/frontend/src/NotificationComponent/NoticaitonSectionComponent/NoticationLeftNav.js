import React, {useEffect, useState} from 'react';
import './NoticationNav.css';
import {Link} from "react-router-dom";

function NotificationLeftNav({check, newCount, oldCount, notifications}) {

    return (

        <div className='notice-nav-wrap'>
            <ul className='notice-box'>
                <Link to="/notification/new">
                    <li className={`${check == 'new' && "click"}`}>
                        <span className='item-head'>읽지 않은 알림</span><span className='item-cnt'>{newCount}</span>
                    </li>
                </Link>
                <Link to="/notification/old">
                    <li className={`${check == 'old' && "click"}`}>
                        <span className='item-head'>읽은 알림</span><span className='item-cnt'>{oldCount}</span>
                    </li>
                </Link>

            </ul>
            <ul className='second-notice-boxs'>
                <li className='second-notice-box'>
                    <span className='item-head'>새 일감 알림</span><span className='item-cnt'>{notifications.WORK_CREATED.length}</span>
                </li>
                <li className='second-notice-box'>
                    <span className='item-head'>구인 참가 신청 알림</span><span className='item-cnt'>{notifications.WORK_UPDATED.length}</span>
                </li>
                <li className='second-notice-box'>
                    <span className='item-head'>관심있는 일감 알림</span><span className='item-cnt'>{notifications.RECRUIT_ENROLLMENT.length}</span>
                </li>
            </ul>
            {
                oldCount>0&&
                <>
                    <button className='remove-btn'>읽은 알림 삭제</button>
                    <small>삭제하지 않아도 한달이 지난 알림은 삭제됩니다.</small>s
                </>
            }
        </div>
    );
}

export default NotificationLeftNav;
