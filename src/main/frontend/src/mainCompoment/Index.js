import React, {useEffect, useState} from 'react';
import './Index.css'
import {useSelector} from "react-redux";
import axiosCo from "../util/common/axiosCommon";
import Card from "./IndexCommonComponet/Card";

function Index({searchData,isSearch}) {
    return (
        <div>
            <div className="container">
                {
                    isSearch ?
                        <div className="py-5 search-text-center ">
                            {searchData.keyword}에 해당하는 일감을{searchData.works.length}개 찾았습니다.
                        </div>:
                        <div className="py-5 text-center">
                            <h2> 전문인력들을 만나다 ExpertPeople</h2>
                        </div>
                }
                <div className="main_flex">
                    {
                        searchData.works&&
                        isSearch&&
                        searchData.works.length>0 &&
                        searchData.works.map(search=>
                          <Card search={search}></Card>
                        )
                    }
                </div>
            </div>
        </div>
    );
}

export default Index;