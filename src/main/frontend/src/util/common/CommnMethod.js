
const commonMethod={
    logout:()=>{
        localStorage.removeItem("jwt");
    }
}

export default commonMethod;