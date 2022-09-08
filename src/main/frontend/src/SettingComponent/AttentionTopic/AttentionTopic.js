import React from 'react';
import LeftMyProfile from "../MyProfile/LeftMyProfile";
import './Attention.css'
import CenterAttention from "./CenterAttention";

import "@yaireo/tagify/dist/tagify.css"
import Tagify from "@yaireo/tagify/dist/tagify.esm";


function AttentionTopic(props) {
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <LeftMyProfile check={'attention'}></LeftMyProfile>
                    <CenterAttention></CenterAttention>
                </div>
            </div>
        </div>
    );
}

export default AttentionTopic;