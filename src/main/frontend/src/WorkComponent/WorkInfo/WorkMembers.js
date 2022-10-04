import React, {useEffect, useState} from 'react';
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import {useSelector} from "react-redux";
import MemberBox from "../WorkCommonComponet/MemberBox";
import './WorkMembers.css'
function WorkMembers(props) {
    const work=useSelector(state=>state.workReducer.work);
    const [managers,setManagers]=useState([]);
    const [members,setMembers]=useState([]);
    useEffect(()=>{
        setManagers(work.managers);
        setMembers(work.members);
    },[])

    return (
        <div className="container ">
            <div className="container-wrap nw-co">
              <WorkHead></WorkHead>
              <WorkNav check='members'></WorkNav>
              <div className='member-box-container'>
                  {
                      managers.length>0&&
                      managers.map((manager)=>
                          <MemberBox member={manager} isManager={true} key={manager.id}></MemberBox>
                      )
                  }
                  {
                      members.length>0&&
                      members.map((member)=>
                          <MemberBox member={member} isManger={false} key={member.id}></MemberBox>)
                  }
              </div>
            </div>
        </div>
    );
}

export default WorkMembers;