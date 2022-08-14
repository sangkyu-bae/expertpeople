import React, {useState} from 'react';
import MainNav from "../mainCompoment/MainNav";

import Alert from "../CommonComponent/Alert";
import Help from "../CommonComponent/Help";
import axios from "axios";
import API from "../util/common/APICommon";
import requests from "../util/common/RequestUrl";
import axiosCo from "../util/common/axiosCommon";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import {loginUser} from "../util/Redux/userReducer";

function Login(props) {

    const[isEmail,setIsEmail]=useState(true);
    const[isPassword,setIsPassword]=useState(true);

    const[email,setEmail]=useState("");
    const[password,setPassword]=useState("");
    const[isServerError,setIsServerError]=useState(false);
    const navigate=useNavigate();
    const dispatch=useDispatch();

    const changeEmail =e=>{
        const regEmail= /^(([^<>()\[\].,;:\s@"]+(\.[^<>()\[\].,;:\s@"]+)*)|(".+"))@(([^<>()[\].,;:\s@"]+\.)+[^<>()[\].,;:\s@"]{2,})$/i;
        if(regEmail.test(e.target.value))setIsEmail(true);
        else setIsEmail(false);
        setEmail(e.target.value);
    }
    const changePassword=e=>{
        if(e.target.value.length<9)setIsPassword(false);
        else setIsPassword(true);
        setPassword(e.target.value);
    }

    const loginSubmit=e=>{
        e.preventDefault();
        if(email.length<5&&!isEmail){
            alert("이메일을 확인하세요");
            return false;
        }
        if(password.length<8&&!isPassword) {
            alert("패스워드를 확인하세요");
            return false;
        }
        const t=axiosCo.login(email,password);
        t.then(t=>{
            console.log(t.data);
            localStorage.setItem("jwt",t.data.token);
            dispatch(loginUser(t.data));
            navigate("/");
        }).catch(t=>setIsServerError(true));
    }

    return (
        <div>
            <div className="container">
                <div className="py-5 text-centers">
                    <p className="lead">ExpertPeople</p>
                    <h2>로그인</h2>
                </div>
                <div className="row justify-content-center">
                    <div className="row justify-content-center">
                        {isServerError &&
                            <div  className="alert alert-danger" role="alert">
                                <p>이메일(또는 닉네임)과 패스워드가 정확하지 않습니다.</p>
                                <p>또는 확인되지 않은 이메일을 사용했습니다. 이메일을 확인해 주세요.</p>
                                <p>
                                    확인 후 다시 입력하시거나, <a>패스워드 찾기</a> 을 이용하세요
                                </p>
                            </div>
                        }

                        <form className="needs-validation col-sm-6" onSubmit={loginSubmit}>
                            <div className="form-group my-3">
                                <input id="username" type="text" name="username" className="form-control"
                                       placeholder="your@email.com"
                                       value={email}
                                       onChange={changeEmail}/>
                                <div>

                                    <small id="emailHelp" className="form-text text-muted">
                                        가입할 때 사용한 이메일을 입력하시오.
                                    </small><br/>
                                    {!isEmail&&<Alert text={"이메일을 입력하세요."}></Alert>}
                                </div>
                            </div>
                            <div className="form-group my-3">
                                <input id="password" type="password" name="password" className="form-control"
                                        value={password} onChange={changePassword}/>
                                <div>
                                    <small id="Help" className="form-text text-muted">
                                        패스워드가 기억나지 않는다면, <a>패스워드 찾기</a>
                                    </small><br/>
                                    {!isPassword&&<Alert text={"패스워드를 입력하세요. 7자 이상입니다."}></Alert>}
                                </div>
                            </div>
                            <div className="form-group">
                                <button className="btn btn-success btn-block" type="submit"
                                        aria-describedby="submitHelp" >로그인
                                </button>
                                <div>
                                    <small id="submitHelp" className="form-text text-muted">
                                        expertPeople 에처음 오신거라면 <a>계정을 먼저 만드세요</a>
                                    </small>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;