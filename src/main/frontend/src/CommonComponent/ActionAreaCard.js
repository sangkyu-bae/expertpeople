import * as React from 'react';
import './Custom.css';
import moment from "moment";
import {Link} from "react-router-dom";
import MainCardView from "../mainCompoment/IndexCommonComponet/MainCardView";
import {useSelector} from "react-redux";

function ActionAreaCard({search, keyword,isSearch}) {
    search.publishedDateTime = moment(search.publishedDateTime).format("YYYY-MM-DD ");
    const isLogin = useSelector(state => state.userReducer.isLogin);
    return (
        <>
            {
                isLogin ?
                    <Link to={`/work/${search.path}`} className='card_box'>
                        <MainCardView search={search} keyword={keyword} isSearch={isSearch}></MainCardView>
                    </Link>
                    :
                    <MainCardView search={search} keyword={keyword} isSearch={isSearch}></MainCardView>
            }
        </>
    );
}

export default ActionAreaCard;