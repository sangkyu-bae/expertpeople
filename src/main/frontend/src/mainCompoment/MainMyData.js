import React, {useEffect, useState} from 'react';
import axiosCo from "../util/common/axiosCommon";
import styled from '@mui/system/styled';
import MainDataLeftComponent from "./MainDataComponent/MainDataLeftComponent";
import MainDataCenterComponent from "./MainDataComponent/MainCenter/MainDataCenterComponent";
import Box from "@mui/system/Box";
import Grid from "@mui/system/Unstable_Grid";
import {Paper} from "@mui/material";
import {MenuList} from "@mui/joy";
import MainDataRightComponent from "./MainDataComponent/MainDataRightComponent";

const Item = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    border: '1px solid',
    borderColor: theme.palette.mode === 'dark' ? '#444d58' : '#ced7e0',
    padding: theme.spacing(1),
    borderRadius: '4px',
    textAlign: 'center',
}));
function MainMyData(props) {
    const[myData,setMyData]=useState({
        isLoading:false,
        loadAccount:{},
        attendWorks:[],
        interestWorks:[],
        managerWorks:[],
        recruitments:[]
    })

    const{loadAccount,attendWorks,interestWorks,managerWorks,recruitments,isLoading}=myData;

    useEffect(()=>{
        getMyData();
    },[])

    const getMyData=()=>{
        axiosCo.getMyData()
            .then(e=>{
                const data=e.data;
                console.log(data)

                setMyData({
                    isLoading:true,
                    loadAccount: data.loadAccount,
                    attendWorks: data.attendFetchWorkVos,
                    interestWorks:data.interestWorkVo,
                    managerWorks:data.managerWorksVos,
                    recruitments:data.recruitmentVos
                })

            })
            .catch(e=>{
                console.log(e)
            })
    }
    return (
      <>
          {
              isLoading&&
              <>
                  <MainDataLeftComponent  jobs={loadAccount.jobs} zones={loadAccount.zone}></MainDataLeftComponent>
                  <MainDataCenterComponent
                      recruitments={recruitments}
                      interestWorks={interestWorks}
                  ></MainDataCenterComponent>
                  <MainDataRightComponent
                      managerWorks={managerWorks}
                      attendWorks={attendWorks}
                  ></MainDataRightComponent>
              </>

          }

      </>
    );
}

export default MainMyData;
