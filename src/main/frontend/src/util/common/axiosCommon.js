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
    },test:async ()=>{
        const res=await accessAPI.post(requests.fetchGetTest)
        return res;
    },updateProfile:async (Profile)=>{
        const res=await accessAPI.post(requests.fetchUpdateProfile,{
                bio:Profile.bio,
                location:Profile.location,
                profileImage:Profile.profileImage,
                job:Profile.job
        })
        return res;
    },updatePassword:async (passwordForm)=>{
        const res=await accessAPI.post(requests.fetchUpdatePassword,{
            password:passwordForm.password,
            passwordCheck : passwordForm.newPassword
        });
        return res
    },getMyJobs:async()=>{
        const res=await accessAPI.get(requests.fetchGetMyJob)
        return res;
    },addMyJobsTags:async(jobs)=>{
        const res=await accessAPI.post(requests.fetchAddMyJobsTags,{
            jobsName:jobs
        })
        return res;
    },removeMyJobsTags:async(jobs)=>{
        const res=await accessAPI.delete(requests.fetchRemoveMyJobsTags, {
                data: {
                    jobsName: jobs
                }
            }
        )
        return res;
    }
}

export default axiosCo;