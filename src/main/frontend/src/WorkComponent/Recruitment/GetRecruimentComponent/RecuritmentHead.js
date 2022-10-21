import React from 'react';

function RecuritmentHead(props) {

    return (
        <div className='flexs'>
            <div className="recruitment-head mg-bt left-flex">
                <h2><span className='re-name'>{props.title}</span> <span>/{props.title}</span></h2>
            </div>
            <div className='right-flex'>
                <button className='blue-btns'>참가신청</button>
            </div>
        </div>
    );
}

export default RecuritmentHead;