import React, {useEffect, useState} from 'react';
import './Index.css'
import {Pagination, Typography} from "@mui/material";
import Paging from "../util/common/Paging";
import paging from "../util/common/Paging";
import ActionAreaCard from "../CommonComponent/ActionAreaCard";
import axiosCo from "../util/common/axiosCommon";
import JoinupHead from "../CommonComponent/JoinupHead";
import MainHead from "./IndexCommonComponet/MainHead";
import {useSelector} from "react-redux";
import MainMyData from "./MainMyData";
function Index({searchData, isSearch}) {
    const [pagination, setPagination] = useState({
        totalCount: 0,
        totalPage: 0,
        pagingPage: 9,
        isNext: true,
        isPrev: false,
        currentPage: 1,
        pageWorks: []
    })
    const [works, setWorks] = useState([])
    const {totalCount, totalPage, pagingPage, pageWorks} = pagination;
    useEffect(() => {
        if (searchData.works) {
            const total = paging.getTotalPage(searchData.works.length, pagingPage);
            const totalCnt = searchData.works.length;
            setWorks(searchData.works);
            setPagination({
                ...pagination,
                totalCount: searchData.works.length,
                totalPage: total,
                pageWorks: Paging.setWorksPage(searchData.works, 1, totalCnt, pagingPage)
            })
        }
    }, [searchData])

    function handleChange(event: React.ChangeEvent<unknown>, value: number) {
        const work = paging.setWorksPage(works, value, totalCount, pagingPage);
        setPagination({
            ...pagination,
            currentPage: value,
            pageWorks: work
        })
    }

    const workChangeSearch = (searchRequirement) => {
        const workpaging = paging[searchRequirement];
        const workData = workpaging(works)
        const work = paging.setWorksPage(workData, 1, totalCount, pagingPage)
        setPagination({
            ...pagination,
            pageWorks: work
        })
    }
    useEffect(() => {
        if (!isSearch) getInitMainWorkData();
    }, [isSearch])

    const getInitMainWorkData=()=>{
        axiosCo.getMainWorkData()
            .then(e=>{
                setWorks(e.data);
            })
            .catch(e=>{
                console.log(e)
            })
    }
    const isLogin=useSelector(state=> state.userReducer.isLogin);
    return (
        <div>
            <div className="container">
                {
                    isSearch ?
                        pageWorks.length > 0 &&
                        <>
                            <MainHead totalCount={totalCount}
                                      searchKeyword={searchData.keyword}
                                      workChangeSearch={workChangeSearch}></MainHead>

                        </>
                        :
                        !isLogin&&
                        <>
                            <JoinupHead></JoinupHead>
                        </>
                }

                <div className="main_flex">
                    {
                        isLogin&&!isSearch&&
                        <MainMyData></MainMyData>
                    }
                    {
                        isLogin&& isSearch &&
                        pageWorks.length > 0 &&
                        pageWorks.map(search =>
                            <ActionAreaCard key={search.id} keyword={searchData.keyword}
                                            search={search}></ActionAreaCard>
                        )
                    }
                    {
                        !isLogin&& !isSearch && works && works.length>0&&
                        works.map(work =>
                            <ActionAreaCard key={work.id} search={work}></ActionAreaCard>
                        )
                    }
                    {
                        isSearch &&
                        totalPage > 1 &&
                        <Pagination count={totalPage}
                                    shape="rounded"
                                    className="pagig-nav"
                                    onChange={handleChange}
                        />
                    }
                </div>
            </div>
        </div>
    );
}

export default Index;