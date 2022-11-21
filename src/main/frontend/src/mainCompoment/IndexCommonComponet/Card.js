import React from 'react';
import './Card.css'
function Card(props) {
    return (
        <div className="card_wrap">
            <div className="card_img">

            </div>
            <div className="card_content">
                <div className="card_middle">
                    <h2 className="card_head">Spring</h2>
                    <small className="card_info">ssss</small>
                    <div className="card_tag_list">
                        <div>태그</div>
                        <div>태그</div>
                        <div>태그</div>
                    </div>
                </div>
                <div className="card_foot">
                    <div className="card_member">0명</div>
                    <div className="card_date">2022년 11월 21일</div>
                </div>
            </div>
        </div>
    );
}

export default Card;
