import React, {useEffect, useState} from 'react';
import './Index.css'
import {Pagination, Typography} from "@mui/material";
import Dropdown from "../CommonComponent/Dropdwon";
import Paging from "../util/common/Paging";
import paging from "../util/common/Paging";
import ActionAreaCard from "../CommonComponent/ActionAreaCard";
import {Marker} from "react-mark.js";
import axiosCo from "../util/common/axiosCommon";
import JoinupHead from "../CommonComponent/JoinupHead";
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

    console.log(searchData.keyword)
    return (
        <div>
            <div className="container">
                {
                    isSearch ?
                        pageWorks.length > 0 &&
                        <>
                            <Marker mark={searchData.keyword} options={{className: "custom-marker-1"}}>
                                <div className="py-5 search-text-center ">
                                    {searchData.keyword}에 해당하는 일감을{totalCount}개 찾았습니다.
                                </div>
                            </Marker>
                            <div className="tet">
                                <Dropdown workChangeSearch={workChangeSearch}></Dropdown>
                            </div>

                        </>
                        :
                        <JoinupHead></JoinupHead>
                }

                <div className="main_flex">
                    {
                        isSearch &&
                        pageWorks.length > 0 &&
                        pageWorks.map(search =>
                            <ActionAreaCard key={search.id} keyword={searchData.keyword}
                                            search={search}></ActionAreaCard>
                        )
                    }
                    {
                        !isSearch && works && works.length>0&&
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