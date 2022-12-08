import MenuItem from "@mui/material/MenuItem";
import React from "react";

class Main{
    constructor(jobs,zones) {
        this.jobs=jobs;
        this.zones=zones;
    }

    getJobsTag(){
        const tagList= this.jobs.map((job,idx)=>{
                if(idx==this.jobs.length-1){
                    return <MenuItem key={job.id}>{job.job}</MenuItem>
                }else{
                    return <MenuItem key={job.id} sx={{ borderBottom: 0.5,borderColor: 'grey.500'}}>{job.job}</MenuItem>
                }
        })

        return tagList
    }


}

export default Main;