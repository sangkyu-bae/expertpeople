import axios from "axios";

export const API=axios.create({
    baseURL:"/",
});

export const accessAPI=axios.create({
    baseURL:"/",
    headers:{
        "Content-Type":"application/json",
        Authorization:`Bearer ${localStorage.getItem("jwt")}`
    }
})
