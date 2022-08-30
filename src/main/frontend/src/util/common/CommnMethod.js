import {useSelector} from "react-redux";

const commonMethod={
    logout:()=>{
        localStorage.removeItem("jwt");
    },
    checkAuth:(reduxUserEmail)=>{
        const localJwt=localStorage.getItem("jwt");
        if(localJwt&&reduxUserEmail)return true
        else return false
    },
}

export default commonMethod;