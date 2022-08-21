import {accessAPI, API} from "./APICommon";
import requests from "./RequestUrl";

const axiosCo={
    login:async (email,password)=>{
        const res=await API.post(requests.fetchLogin,{
            email:email,
            password:password
        })
        return res;
    },
    myInfo:async (email)=>{
        const res=await accessAPI.get(requests.fetchGetAccountInfo,{
            params:{
                email:email
            }
        })
        return res;
    }

}

export default axiosCo;