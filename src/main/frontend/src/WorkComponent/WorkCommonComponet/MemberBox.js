import React, {useEffect} from 'react';
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function MemberBox(props) {
    return (
        <div className='member-box'>
            <div className='member-box-img'>
            {
                props.member.profileImage!=null ?
                    <img src={props.member.profileImage}></img>:
                    <FontAwesomeIcon className="img" icon={faAddressCard}></FontAwesomeIcon>
            }
            </div>
            <div className='member-box-info'>
                <div>
                    <span className='member-name'>{props.member.name}</span>
                    {
                        props.isManager &&<span className='member-manager'>관리자</span>
                    }
                </div>
                <div className='member-bio'>
                    {props.member.bio}
                </div>
            </div>
        </div>
    );
}

export default MemberBox;