import MenuItem from "@mui/material/MenuItem";
import React from "react";
import LocationOnIcon from "@mui/icons-material/LocationOn";

class Main{
    constructor() {
    }

    getJobsTag(jobs){
        const tagList= jobs.map((job,idx)=>{
                if(idx===jobs.length-1){
                    return <MenuItem key={job.id}>{job.job}</MenuItem>
                }else{
                    return <MenuItem key={job.id} sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>{job.job}</MenuItem>
                }
        })
        return tagList;
    }

    getZoneTag(zones){
        const zoneList=zones.map((zone,idx)=>{
            if(idx===zones.length-1){
                return <MenuItem icon={<LocationOnIcon/>}  key={zone.id}>{zone.localNameOfCity}</MenuItem>
            }else{
                return <MenuItem icon={<LocationOnIcon/>}  key={zone.id} sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>{zone.localNameOfCity}</MenuItem>
            }
        })
        return zoneList;
    }

    getMainTag(dataList){
        const list=dataList.map((data,idx)=>{
            if(idx===dataList.length-1){
                return <MenuItem icon={<LocationOnIcon/>}  key={data.id}>{data.title}</MenuItem>
            }else{
                return <MenuItem icon={<LocationOnIcon/>}  key={data.id} sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>{data.title}</MenuItem>
            }
        })

        return list;
    }
}

export default Main;