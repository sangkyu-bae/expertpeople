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

export const accTE=()=>{
    console.log(localStorage.getItem("jwt"));
    if(jwt){
        const accessApi=axios.create({
            baseURL:"/",
            headers:{
                "Content-Type":"application/json",
                Authorization:`Bearer ${localStorage.getItem("jwt")}`
            }
        })
        return accessApi;
    }
}
