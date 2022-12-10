import React, {useEffect, useState} from 'react';
import Box from "@mui/system/Box";
import Grid from "@mui/system/Unstable_Grid";
import {Paper} from "@mui/material";
import {MenuList} from "@mui/joy";
import Main from "../../util/MainService/Main";

function MainDataRightComponent({attendWorks,managerWorks}) {
    const[rightDataList,setRightDataList]=useState({
        attendTag:[],
        managerWorkTag:[]
    })
    useEffect(()=>{
       const main=new Main();
       const attendWork=main.getMainTag(attendWorks);
       const managerWork=main.getMainTag(managerWorks);

       setRightDataList({
           attendTag: attendWork,
           managerWorkTag: managerWork
       })
    },[])
    const{attendTag,managerWorkTag}=rightDataList;
    return (
        <div className="right-content">
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2}>
                    <div className="main_head">관리중인 일감</div>
                    <Grid xs={10}>
                        <Paper sx={{md:5}}>
                            <MenuList id={"interest_box"}>
                                {
                                    managerWorkTag.length>0&&
                                    managerWorkTag.map(work=>work)
                                }
                            </MenuList>
                        </Paper>
                    </Grid>
                    <Grid xs={10}>
                        <div className="main_head">참가중인 일감</div>
                        <Paper>
                            <MenuList id={"interest_box"}>
                                {
                                    attendTag.length>0&&
                                    attendTag.map(work=>work)
                                }
                            </MenuList>
                        </Paper>
                    </Grid>
                </Grid>
            </Box>
        </div>
    );
}

export default MainDataRightComponent;