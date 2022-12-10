import React, {useEffect, useState} from 'react';
import Box from "@mui/system/Box";
import Grid from "@mui/system/Unstable_Grid";
import {Paper} from "@mui/material";
import {MenuList} from "@mui/joy";
import MenuItem from "@mui/material/MenuItem";
import Main from "../../util/MainService/Main";

function MainDataLeftComponent({jobs,zones,main}) {


    const [leftList,setLeftList]=useState({
        jobTag:[],
        zoneTag:[]
    })
    const {jobTag,zoneTag}=leftList;
    useEffect(()=>{
        let main=new Main();
        const tagList=main.getJobsTag(jobs)
        const zoneList=main.getZoneTag(zones);
        setLeftList({
            jobTag: tagList,
            zoneTag: zoneList
        });
    },[])
    return (
        <div className="left-content">
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2}>
                    <div className="main_head">구인 직업</div>
                    <Grid xs={10}>
                        <Paper sx={{md:5}}>
                            <MenuList id={"interest_box"}>
                                {
                                    jobTag.length>0&&
                                    jobTag.map(job=>job)
                                }
                            </MenuList>
                        </Paper>
                    </Grid>
                    <Grid xs={10}>
                        <div className="main_head">구인 지역</div>
                        <Paper>
                            <MenuList id={"interest_box"}>
                                {
                                    zoneTag.length>0&&
                                    zoneTag.map(zone=>zone)
                                }
                            </MenuList>
                        </Paper>
                    </Grid>
                </Grid>
            </Box>
        </div>
    );
}

export default MainDataLeftComponent;
