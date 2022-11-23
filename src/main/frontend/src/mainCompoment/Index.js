import React, {useEffect, useState} from 'react';
import './Index.css'
import Card from "./IndexCommonComponet/Card";
import {Pagination, Typography} from "@mui/material";



function Index({searchData,isSearch}) {
    const [pagination,setPagination]=useState({
        totalCount:0,
        totalPage:0,
        pagingPage:9,
        isNext:true,
        isPrev:false,
        currentPage:1,
        pageWorks:[]
    })
    const [works,setWork]=useState()
    const{totalCount,totalPage,pagingPage,isNext,isPrev,currentPage,pageWorks}=pagination;
    useEffect(()=>{
        if(searchData.works){
            console.log(searchData)
            setWork(searchData);
            setPagination({
                ...pagination,
                totalCount: searchData.length,
                totalPage: getTotalPage(searchData.works.length),
                pageWorks: setWorksPage(searchData.works)
            })
        }
    },[searchData])

    const getTotalPage=(totalCount)=>{
        const tt=totalCount/pagingPage;
        return Math.floor(tt);
    }
    const setWorksPage=(worksData)=>{
        const work=[];
        let i=(currentPage-1)*pagingPage;
        let brenchmark=currentPage*pagingPage

        for(i;i<brenchmark;i++){
            work.push(worksData[i]);
        }

        return work;
    }

    function handleChange(event: React.ChangeEvent<unknown>, value: number) {

    }

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
                    {/*{*/}
                    {/*    searchData.works&&*/}
                    {/*    isSearch&&*/}
                    {/*    searchData.works.length>0 &&*/}
                    {/*    searchData.works.map(search=>*/}
                    {/*      <Card key={search.id} search={search}></Card>*/}
                    {/*    )*/}
                    {/*}*/}
                    {
                        isSearch&&
                        pageWorks.length>0 &&
                        pageWorks.map(search=>
                            <Card key={search.id} search={search}></Card>
                        )
                    }
                    {
                        isSearch&&
                        totalPage>0 &&
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