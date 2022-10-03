import React from 'react';
import {faAddressCard} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function MemberBox(props) {
    return (
        <div>
            <div>
            {
                props.member.profileImage!='' ?
                    <img src={props.member.profileImage}></img>:
                    <FontAwesomeIcon className="img" icon={faAddressCard}></FontAwesomeIcon>
            }
            </div>
            <div>
                <div>
                    <span>{props.member.name}</span>
                    {
                        props.isManager &&<span>관리자</span>
                    }
                </div>
                <div>
                    {props.member.bio}
                </div>
            </div>
        </div>
    );
}

export default MemberBox;