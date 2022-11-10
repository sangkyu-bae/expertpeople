import React from 'react';
import './NoticationNav.css';
function NotificationLeftNav({check,newCount,oldCount}) {
    return (
        <div className='notice-nav-wrap'>
            <ul className='notice-box'>
                <li className={`${check=='new'&&"click"}`}>
                    <span className='item-head'>읽지 않은 알림</span><span className='item-cnt'>{newCount}</span>
                </li>
                <li>
                    <span className='item-head'>읽은 알림</span><span className='item-cnt'>{oldCount}</span>
                </li>
            </ul>
            <ul className='notice-box'>
                <li>
                    <span className='item-head'>구인 참가 신청 알림</span><span className='item-cnt'>0</span>
                </li>
                <li>
                    <span className='item-head'>관심있는 일감 알림</span><span className='item-cnt'>0</span>
                </li>
            </ul>
            <button className='remove-btn'>읽은 알림 삭제</button>
            <small>삭제하지 않아도 한달이 지난 알림은 삭제됩니다.</small>
        </div>
    );
}

export default NotificationLeftNav;
