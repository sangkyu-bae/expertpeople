let isCheck=true;
const regexp=(regString)=>{
    const reg=/[ㄱ-ㅎ가-힣a-z0-9_-]/g
    const isReg= reg.test(regString);
    return isReg;
}

const formValidation={
    newWorkValidation:(inputs)=>{
        const isRex=regexp(inputs.url)
        if(inputs.url.length<3 || inputs.url.length>20||!isRex)isCheck=false;
        else if(inputs.workTitle.length<3||inputs.workTitle.length>50) isCheck=false;
        else if(inputs.shortInfo.length<3||inputs.shortInfo.length>100) isCheck=false;
        else if(inputs.workInfo.length<3||inputs.workInfo.length>600) isCheck=false;
        return isCheck;
    },
    updateDescription(inputs) {
        if(inputs.fullDescription.length<3)isCheck=false;
        if(inputs.shortDescription.length<3||inputs.shortDescription.length>100)isCheck=false;
        return isCheck;
    }
}


export default formValidation;