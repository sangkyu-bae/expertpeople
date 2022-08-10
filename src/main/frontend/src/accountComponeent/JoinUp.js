import React, {useState} from 'react';
import '../LoginComponent/Login.css';
import  './JoinUp.css'
import axios from "axios";
import Error from "./AccountCommonComponent/Error";
function JoinUp(props) {
     const [inputs,setInputs]=useState({
         name:'',
         nickname:'',
         email:'',
         password:'',
     })


    const [isNickname,setIsNickname]=useState(true);
    const [isName,setIsName]=useState(true);
    const [isEmail,setIsEmail]=useState(true);
    const [isPasswrod,setIsPassword]=useState(true);

    const {name,nickname,email,password}=inputs;
     const onChange =e=>{
         const{name,value}=e.target;
         setInputs({
             ...inputs,
             [name]:value
         })
     }

    const [rePasswrod,setRePassword]=useState("");
    const changeRePasswrod=e=>setRePassword(e.target.value);
    const [serverErrors,setServerErrors]=useState({
        isServerEmail:false,
        isServerPassword:false,
        isServerNickname:false,
        isServerName:false
    })


    const onSubmit=e=>{
        e.preventDefault();
        const regEmail= /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        if(name.length<3) setIsName(false);

        if(nickname.length<4)setIsNickname(false)
        if(!regEmail.test(email)) setIsEmail(false);
        if(password.length<9||rePasswrod.length<9) setIsPassword(false);
        if(password!=rePasswrod) setIsPassword(false)

        console.log(isName)
        if(isEmail&&isPasswrod&&isName&&isNickname){
            axios.post("/api/join-up",inputs)
                .then(e=> console.log(e))
                .catch(e=>{
                    console.log(e);
                    console.log(e.request.response.indexOf("fidId"));
                    console.log(e.request.response);

                    if(e.request.response.indexOf("email")>0){

                    }
                })
        }else{
            alert("입력하지 않은 요소를 확인하세요.")
        }
    }
    return (
        <div>
            <div className="container">
                <div className="py-5 text-center">
                    <h2> 회원가입</h2>
                </div>
                <div className="row justify-content-center">
                    <form className="needs-validation col-sm-6"
                          method="post"onSubmit={onSubmit}>
                        <div className="form-group py-1">
                            <label htmlFor="name">이름</label>
                            <input type="text" className="form-control" id="name" name="name"
                                   onChange={onChange} value={name}
                                    placeholder="이름을 입력하세요"   maxLength="20"/>
                                <small id="nameHelp" className="form-text text-muted">가입할 회원의 이름을 입력하세요</small><br/>
                                {!isName && <Error nullText={"이름을 입력하세요."}></Error>}
                                <small className="form-text text-danger">Nickname Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="nickname">닉네임</label>
                            <input id="nickname" type="text"  className="form-control"
                                   placeholder="whiteship"  required minLength="3"
                                   onChange={onChange} value={nickname} name="nickname"
                                   maxLength="20"/>
                                <small id="nicknameHelp" className="form-text text-muted">
                                    공백없이 문자와 숫자로만 3자 이상 20자 이내로 입력하세요. 가입후에 변경할 수 있습니다.
                                </small><br/>
                                {!isNickname&& <Error nullText={"닉네임을 입력하세요."}></Error>}
                                <small className="form-text text-danger">Nickname Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="email">이메일</label>
                            <input id="email" type="email" className="form-control"
                                   placeholder="your@email.com" aria-describedby="emailHelp"
                                   onChange={onChange} value={email} name='email'
                                   required/>
                                <small id="emailHelp" className="form-text text-muted">
                                    ExpertPeople 는 사용자의 이메일을 공개하지 않습니다.
                                </small><br/>
                                {!isEmail&& <Error nullText={"이메일을 입력하세요."}></Error>}
                                <small className="invalid-feedback">이메일을 입력하세요.</small>
                                <small className="form-text text-danger">Email Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="password">패스워드</label>
                            <input id="password" type="password"className="form-control"
                                   onChange={onChange} value={password} name='password'
                                   required minLength="8" maxLength="50"/>
                                <small id="passwordHelp" className="form-text text-muted">
                                    8자 이상 50자 이내로 입력하세요. 영문자, 숫자, 특수기호를 사용할 수 있으며 공백은 사용할 수 없습니다.
                                </small><br/>
                                {!password&& <Error nullText={"패스워드를 입력하세요."}></Error>}
                                <small className="form-text text-danger">Password Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="password-check">패스워드 확인</label>
                            <input id="password-check" type="password" className="form-control"
                                   value={rePasswrod} onChange={changeRePasswrod} name="rePassword"
                                    required minLength="8" maxLength="50"/>
                                <small id="password-checkHelp" className="form-text text-muted">
                                </small>
                                <small className="invalid-feedback">패스워드 확인을 입력하세요.</small>
                        </div>
                        <div className="form-group py-1">
                            <button type="submit" className="btn btn-primary btn-block">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default JoinUp;