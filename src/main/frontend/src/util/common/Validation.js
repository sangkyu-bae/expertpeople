import moment from "moment/moment";
const dateForm='YYYY-MM-DD';
const nowDate=moment().format(dateForm);
let isCheck=true;

export const regexp=(regString)=>{
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
    },
    updateNewTitle(newTitle){
        if(newTitle.length<3||newTitle.length>50) isCheck=false;
        return isCheck;
    },updateRecruit(inputs){
        const endEnrollmentDateTime=moment(inputs.endEnrollmentDateTime).format(dateForm);
        const startDateTime=moment(inputs.startDateTime).format(dateForm);
        const endDateTime=moment(inputs.endDateTime).format(dateForm);

        if (inputs.title.length>50||inputs.title.length<2) isCheck=false;
        if (inputs.description.length<5) isCheck=false;
        if(moment(nowDate).isBefore(endEnrollmentDateTime)) isCheck=false;
        if(moment(nowDate).isBefore(startDateTime)) isCheck=false;
        if(moment(startDateTime).isBefore(endDateTime)) isCheck=false;
        if(inputs.limitOfEnrollments<2) isCheck=false;

        return isCheck;
    }
}


export default formValidation;