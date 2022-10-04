import React, {useState} from 'react';
import SettingNav from "../WorkCommonComponet/SettingNav";
import WorkHead from "../WorkCommonComponet/WorkHead";
import WorkNav from "../WorkCommonComponet/WorkNav";
import {CKEditor} from "@ckeditor/ckeditor5-react";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

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

    return (
        <>
            <div className='work-left-nav'>
                <SettingNav check={props.settingNavCheck}></SettingNav>
            </div>
            <div className='work-setting-content'>
                <WorkHead></WorkHead>
                <WorkNav check={props.workNavCheck}></WorkNav>
                <div className='work-setting-input-box'>
                    <form>
                        <div className="work-content short">
                            <h2>짧은 소개</h2>
                            <input  value={shortDescription} name="shortDescription" type="text" placeholder="일감을 짧게 소개해 주세요"/>
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
