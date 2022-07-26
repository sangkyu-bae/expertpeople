import React from 'react';
import './Card.css'
import moment from "moment/moment";
import {Link} from "react-router-dom";
function Card({search}) {
    search.publishedDateTime=moment(search.publishedDateTime).format("YYYY-MM-DD ");
    return (
        <div className="card_wrap">
            <div className="card_img">

            </div>
            <div className="card_content">
                <div className="card_middle">
                    <Link to={`/work/${search.path}`}>
                        <h2 className="card_head">{search.title}</h2>
                    </Link>
                    <small className="card_info">{search.shortDescription}</small>
                    <div className="card_tag_list">
                        <div>태그</div>
                        <div>태그</div>
                        <div>태그</div>
                    </div>
                </div>
                <div className="card_foot">
                    <div className="card_member">{search.members.length}명</div>
                    <div className="card_date">{search.publishedDateTime}</div>
                </div>
            </div>
        </div>
    );
}

export default Card;
