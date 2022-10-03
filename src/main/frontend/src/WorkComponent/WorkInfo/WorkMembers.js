import React, {useEffect, useState} from 'react';
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "./WorkCommonComponet/WorkHead";
import WorkNav from "./WorkCommonComponet/WorkNav";
import {useSelector} from "react-redux";
import MemberBox from "./WorkCommonComponet/MemberBox";

function WorkMembers(props) {
    const work=useSelector(state=>state.workReducer.work);
    const [managers,setManagers]=useState([]);
    const [members,setMembers]=useState([]);
    useEffect(()=>{
        setManagers(work.managers);
        setMembers(work.members);
    },[])
    const managerList=managers.map((manager)=>{
        <MemberBox member={manager} isManager={true}></MemberBox>
    })

    useEffect(()=>{
        if(managers.length>0)console.log(managers)

    },[managers,members])

    return (
        <div className="container ">
            <div className="container-wrap nw-co">
              <WorkHead></WorkHead>
              <WorkNav check='members'></WorkNav>
              <div>

              </div>
            </div>
        </div>
    );
}

export default WorkMembers;