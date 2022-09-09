package com.expertpeople.modules.job.form;

import com.expertpeople.modules.job.Carrer;
import lombok.Data;

@Data
public class JobForm {

    private String jobsName;

    public String getJobName(){
        return jobsName.substring(0,jobsName.indexOf("("));
    }

    public Enum getCarrer(){
        Carrer carrer;
        String carrerName=jobsName.substring(jobsName.indexOf("(")+1,jobsName.indexOf(")"));
        if (carrerName.equals("일반")){
           carrer=Carrer.NORMAL;
        }else{
            carrer=Carrer.TECH;
        }
        return carrer;
    }



}
