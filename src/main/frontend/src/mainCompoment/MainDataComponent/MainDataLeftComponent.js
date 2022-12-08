import React, {useEffect, useState} from 'react';
import Box from "@mui/system/Box";
import Grid from "@mui/system/Unstable_Grid";
import {Paper} from "@mui/material";
import {MenuList} from "@mui/joy";
import MenuItem from "@mui/material/MenuItem";
import Main from "../../util/MainService/Main";

function MainDataLeftComponent({jobs,zones,main}) {

    const [jobTag,setJobTag]=useState([]);
    useEffect(()=>{
        let main=new Main(jobs,zones);
        const tagList=main.getJobsTag()
        setJobTag(tagList)
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

                                {/*<MenuItem sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>Profile</MenuItem>*/}
                                {/*<MenuItem sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>My account</MenuItem>*/}
                                {/*<MenuItem >Logout</MenuItem>*/}
                            </MenuList>
                        </Paper>
                    </Grid>
                    <Grid xs={10}>
                        <div className="main_head">구인 지역</div>
                        <Paper>
                            <MenuList id={"interest_box"}>
                                <MenuItem sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>Profile</MenuItem>
                                <MenuItem sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>My account</MenuItem>
                                <MenuItem >Logout</MenuItem>
                            </MenuList>
                        </Paper>
                    </Grid>
                </Grid>
            </Box>
        </div>
    );
}

export default MainDataLeftComponent;
