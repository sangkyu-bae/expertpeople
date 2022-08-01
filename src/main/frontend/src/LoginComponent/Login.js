import React from 'react';
import MainNav from "../mainCompoment/MainNav";
import './Login.css';

function Login(props) {
    return (
        <div>
            <div className="container">
                <div className="py-5 text-center">
                    <p className="lead">ExpertPeople</p>
                    <h2>로그인</h2>
                </div>
                <div className="row justify-content-center">
                    <div className="row justify-content-center">
                        <div  className="alert alert-danger" role="alert">
                            <p>이메일(또는 닉네임)과 패스워드가 정확하지 않습니다.</p>
                            <p>또는 확인되지 않은 이메일을 사용했습니다. 이메일을 확인해 주세요.</p>
                            <p>
                                확인 후 다시 입력하시거나, <a>패스워드 찾기</a> 을 이용하세요
                            </p>
                        </div>
                        <form className="needs-validation col-sm-6" action="#" method="post"
                              noValidate>
                            <div className="form-group my-3">
                                <input id="username" type="text" name="username" className="form-control"
                                       placeholder="your@email.com" aria-describedby="emailHelp" required/>
                                <div>
                                    <small id="emailHelp" className="form-text text-muted">
                                        가입할 때 사용한 이메일을 입력하시오.
                                    </small>
                                    <small className="invlid-feedback"> 이메일을 입력하세요.</small>
                                </div>
                            </div>
                            <div className="form-group my-3">
                                <input id="password" type="password" name="password" className="form-control"
                                       aria-describedby="passwordHelp" required/>
                                <div>
                                    <small id="passwordHelp" className="form-text text-muted">
                                        패스워드가 기억나지 않는다면, <a>패스워드 찾기</a>
                                    </small>
                                    <small className="invlid-feedback"> 패스워드를 입력하세요.</small>
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