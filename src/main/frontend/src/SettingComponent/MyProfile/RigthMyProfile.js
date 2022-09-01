import React, {useEffect, useRef, useState} from 'react';
import UserImg from "../SettingCommon/UserImg";
import {Cropper} from "react-cropper";

function RigthMyProfile(props) {
    const [fileImage,setFileImage]=useState("");
    const [isImage,setIsImage]=useState(false);
    const [base64,setBase64]=useState("");
    const changeImage=(e)=>{
        setIsImage(true);
        setFileImage(URL.createObjectURL(e.target.files[0]));

        let reader=new FileReader();
        reader.readAsDataURL(e.target.files[0]);
        reader.onload=()=>setBase64(reader.result);
    }

    const cancelImage=()=>{
        setFileImage("");
        setIsImage(false);
    }
    return (
        <div className="profile-right">
            <div className="right-head">
                <span>프로필 이미지</span>
            </div>
            <div className="right-center">
                <div className="img-box">
                    {
                        !isImage?
                            <UserImg></UserImg>:
                            fileImage!=""?<img className="thumb-nail" src={fileImage}/>
                            // <Cropper
                            // src={fileImage}
                            // style={{ height: "100%", width: "100%" }}
                            // initialAspectRatio={16 / 9}
                            // guides={false}
                            // ></Cropper>
                                :<></>
                    }

                </div>
                <div className="filebox preview-image">
                    <input className="upload-name" value="파일선택" disabled="disabled"/>
                    <label htmlFor="input-file">업로드</label>
                    <input type="file" onChange={changeImage}  id="input-file" className="upload-hidden"/>
                </div>
                {
                    isImage&&
                    <div className="sub-set">
                        {/*<button className="cut">자르기</button>*/}
                        <button className="cancel" onClick={cancelImage}>취소</button>
                    </div>
                }
            </div>
        </div>
    );
}

export default RigthMyProfile;