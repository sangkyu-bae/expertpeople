import React, {useState} from 'react';
import SettingNav from "../WorkCommonComponet/SettingNav";
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import validation from "../../util/common/Validation";
import axios from "axios";
import axiosCo from "../../util/common/axiosCommon";
import {useParams} from "react-router-dom";
import NewWorkAlert from "../NewWork/NewWorkAlert";

function WorkSettingHead(props) {
    const [inputs,setInputs]=useState({
        shortDescription:'',
        fullDescription:''
    })
    const {shortDescription,fullDescription}=inputs;
    const onChange=e=>{
        const {name,value}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        })
    }

    const path=useParams();
    const[errors,setErrors]=useState({
        isError:false,
        errorMessage:''
    })
    const {isError,errorMessage}=errors;

    const [isSuccess,setIsSuccess]=useState(false);
    const onSubmit=e=>{
        e.preventDefault();
        const check=validation.updateDescription(inputs);
        if(check) updateDescription();
        else {
            setErrors({
                ...errors,
                isError: true,
                errorMessage: '입력된 데이터가 잘 못되었습니다.'
            })
        }
    }
    const updateDescription=()=>{
        axiosCo.updateDescription(inputs,path.path)
            .then(e=>{
                setIsSuccess(true);
            })
            .catch(e=>{
               setErrors({
                   ...errors,
                   isError: true,
                   errorMessage: e.response.data.message
               })
            })
    }

    return (
        <>
            {
                isError &&
                <NewWorkAlert text={errorMessage}></NewWorkAlert>
            }
            {
               isSuccess&&
                <div className="topic-content locations">
                   일감 수정이 완료 되었습니다.
                </div>
            }
            <div className='work-left-nav'>
                <SettingNav check={props.settingNavCheck} path={path}></SettingNav>
            </div>
            <div className='work-setting-content'>
                <WorkHead></WorkHead>
                <WorkNav check={props.workNavCheck}></WorkNav>
                <div className='work-setting-input-box'>
                    <form onSubmit={onSubmit}>
                        <div className="work-content short">
                            <h2>짧은 소개</h2>
                            <input onChange={onChange} value={shortDescription} name="shortDescription" type="text" placeholder="일감을 짧게 소개해 주세요"/>
                        </div>
                        <div className="work-content">
                            <h2 className="work-infos">상세 소개</h2>
                            <CKEditor
                                editor={ ClassicEditor }
                                data=""
                                onReady={ editor => {
                                    // You can store the "editor" and use when it is needed.
                                    console.log( 'Editor is ready to use!', editor );
                                } }
                                onChange={ ( event, editor ) => {
                                    const data = editor.getData();
                                    setInputs({
                                        ...inputs,
                                        fullDescription: data
                                    })
                                    console.log( { event, editor, data } );
                                } }
                                onBlur={ ( event, editor ) => {
                                    console.log( 'Blur.', editor );
                                } }
                                onFocus={ ( event, editor ) => {
                                    console.log( 'Focus.', editor );
                                } }
                            />
                        </div>
                        <button className='password-btn new-study-btn'>일감 수정</button>
                    </form>
                </div>
            </div>
        </>
    );
}

export default WorkSettingHead;
