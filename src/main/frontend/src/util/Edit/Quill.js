import React, {useState} from 'react';
import ReactQuill from "react-quill";

function Quill(props) {
    const [htmlContent,setHtmlContent]=useState("");
    const modules = {
        toolbar: [
            //[{ 'font': [] }],
            [{ 'header': [1, 2, false] }],
            ['bold', 'italic', 'underline','strike', 'blockquote'],
            [{'list': 'ordered'}, {'list': 'bullet'}, {'indent': '-1'}, {'indent': '+1'}],
            ['link', 'image'],
            [{ 'align': [] }, { 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
            ['clean']
        ],
    }

    const formats = [
        //'font',
        'header',
        'bold', 'italic', 'underline', 'strike', 'blockquote',
        'list', 'bullet', 'indent',
        'link', 'image',
        'align', 'color', 'background',
    ]

    return (
        <div>
            <ReactQuill
                style={{height:"600px"}}
                theme="snow"
                modules={modules}
                formats={formats}
                value={htmlContent}
                onChange={(content,data,source,editor)=> onchange(editor.getHTML())}
            >

            </ReactQuill>
        </div>
    );
}

export default Quill;