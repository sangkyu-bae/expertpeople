import React, {useState} from 'react';
import './WorkSetting.css';
import WorkSettingHead from "./WorkSettingHead";
import NewWorkAlert from "../NewWork/NewWorkAlert";
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import {useParams} from "react-router-dom";
import validation from "../../util/common/Validation";
import axiosCo from "../../util/common/axiosCommon";
import SettingNav from "../WorkCommonComponet/SettingNav";
import Tags from "@yaireo/tagify/dist/react.tagify";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

function WorkSetting(props) {
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
        <div className="container work-setting-wrap">
            <div className="container-wrap nw-co ">
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
                <WorkHead></WorkHead>
                <WorkNav check='setting'></WorkNav>
                <div className='flex'>
                    <SettingNav check='info' path={path}></SettingNav>
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
                    {/*<div className='work-setting-content'>*/}
                    {/*    <div className="topic-wrap">*/}
                    {/*        <div className="topic-head">*/}
                    {/*            <h2>일터지역</h2>*/}
                    {/*        </div>*/}
                    {/*        <div className="topic-content locations">*/}
                    {/*            생성된 일감의 지역을 선택해 주세요. 태그를 입력하고 콤마(,)또는 엔터를 입력하세요.*/}
                    {/*        </div>*/}
                    {/*        <div>*/}
                    {/*            {*/}
                    {/*                whitelist.length>0&&*/}
                    {/*                <Tags*/}
                    {/*                    settings={settings}*/}
                    {/*                    value={initZones}*/}
                    {/*                ></Tags>*/}
                    {/*            }*/}
                    {/*        </div>*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                </div>
                {/*<WorkSettingHead workNavCheck='setting' settingNavCheck='info'></WorkSettingHead>*/}
            </div>
        </div>
    );
}

export default WorkSetting;