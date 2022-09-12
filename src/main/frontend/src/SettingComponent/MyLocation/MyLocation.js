import React from 'react';
import LeftMyProfile from "../MyProfile/LeftMyProfile";
import Tags from "@yaireo/tagify/dist/react.tagify";
import MyLocationCenter from "./MyLocationCenter";

function MyLocation(props) {
    return (
        <div className="container">
            <div className="container-wrap">
                <div className="flex">
                    <LeftMyProfile check={'location'}></LeftMyProfile>
                    <MyLocationCenter></MyLocationCenter>
                </div>
            </div>
        </div>
    );
}

export default MyLocation;