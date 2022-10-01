import React, {useState} from 'react';
import LeftMyProfile from "../../SettingComponent/MyProfile/LeftMyProfile";
import CenterAttention from "../../SettingComponent/AttentionTopic/CenterAttention";
import './NewWork.css';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import validation from "../../util/common/Validation";
import axiosCo from "../../util/common/axiosCommon";
import NewWorkAlert from "./NewWorkAlert";
import {useNavigate} from "react-router-dom";
function NewWork(props) {

    const [inputs,setInputs]=useState({
        url:'',
        workTitle:'',
        shortInfo:'',
        workInfo:''
    })
    const{url,workTitle,shortInfo,workInfo}=inputs;
    const onChange=e=>{
        const{name,value}=e.target;
        setInputs({
            ...inputs,
            [name]:value
        });
    }
    const [isInputsError,setIsInputError]=useState({
        isFrontError:false,
        isBackError:false
    })
    const{isFrontError,isBackError}=isInputsError;
    const onSubmit =e=>{
        e.preventDefault();
        const check=validation.newWorkValidation(inputs)
        if(check){
          newWork()
        }else {
            setIsInputError({
                ...isInputsError,
                isFrontError:true
            })
        }
    }
    const nav=useNavigate();
    const newWork=()=>{
        axiosCo.addWork(inputs)
            .then(e=>{
               nav(`/work/${e.data}`);
            })
            .catch(e=>{
                setIsInputError({
                        ...setIsInputError,
                        isBackError:true
                })
            })
    }
    return (
        <div className="container ">
            <div className="container-wrap nw-co">
                <div className="content-wrap">
                    {
                        isFrontError&&
                        <NewWorkAlert text={"입력요소가 정확하지 않습니다."}></NewWorkAlert>
                    }
                    {
                        isBackError&&
                        <NewWorkAlert text={"중복된 경로 입니다."}></NewWorkAlert>
                    }
                    <div className="work-head-wrap">
                        <h1>일감 개설</h1>
                    </div>

                    <form onSubmit={onSubmit}>
                        <div className="work-head-content">
                            <div className="work-content">
                                <h2>일감 URL</h2>
                                <input value={url} onChange={onChange} name="url" type="text"placeholder="예) work-path"/>
                            </div>
                            <div className="work-content">
                                <h2>일감 이름</h2>
                                <input value={workTitle} onChange={onChange} name="workTitle" type="text" placeholder="일감 이름"/>
                            </div>
                            <div className="work-content short">
                                <h2>짧은 소개</h2>
                                <input value={shortInfo} onChange={onChange} name="shortInfo" type="text" placeholder="일감을 짧게 소개해 주세요"/>
                            </div>
                            <div className="work-content">
                                <h2 className="work-info">상세 소개</h2>
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
                                            workInfo: data
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
                            <button className='password-btn new-study-btn'>스터디 만들기</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default NewWork;