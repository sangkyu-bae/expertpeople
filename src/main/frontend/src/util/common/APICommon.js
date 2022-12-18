import axios from "axios";
import {getCookie} from "./Cookies";
import {useEffect, useState} from "react";

export const API=axios.create({
    baseURL:"/",
});

export const accessAPI=axios.create({
    baseURL:"/",
    headers:{
        "Content-Type":"application/json",
        //Authorization:`Bearer ${localStorage.getItem("jwt")}`
        // Authorization:`Bearer ${getCookie('jwt')}`
        Authorization:`Bearer ${getCookie('jwt')}`
    }
})

export const updateAPI=()=>{
    const jwt=getCookie('jwt')
    const accessAPIs=axios.create({
        baseURL: "/",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${jwt}`
        }
    })
    return accessAPIs;
}

