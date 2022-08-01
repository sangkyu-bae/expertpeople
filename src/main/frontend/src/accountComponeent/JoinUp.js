import React from 'react';
import MainNav from "../mainCompoment/MainNav";

function JoinUp(props) {
    return (
        <div>
            <div className="container">
                <div className="py-5 text-center">
                    <h2> 회원가입</h2>
                </div>
                <div className="row justify-content-center">
                    <form className="needs-validation col-sm-6"
                          method="post" noValidate>
                        <div className="form-group py-1">
                            <label htmlFor="name">이름</label>
                            <input type="text" className="form-control" id="name"
                                   aria-describedby="nameHelp" placeholder="이름을 입력하세요" required minLength="2"
                                   maxLength="20"/>
                                <small id="nameHelp" className="form-text text-muted">가입할 회원의 이름을 입력하세요</small>
                                <small className="invalid-feedback">닉네임을 입력하세요.</small>
                                <small className="form-text text-danger">Nickname Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="nickname">닉네임</label>
                            <input id="nickname" type="text"  className="form-control"
                                   placeholder="whiteship" aria-describedby="nicknameHelp" required minLength="3"
                                   maxLength="20"/>
                                <small id="nicknameHelp" className="form-text text-muted">
                                    공백없이 문자와 숫자로만 3자 이상 20자 이내로 입력하세요. 가입후에 변경할 수 있습니다.
                                </small>
                                <small className="invalid-feedback">닉네임을 입력하세요.</small>
                                <small className="form-text text-danger">Nickname Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="email">이메일</label>
                            <input id="email" type="email" className="form-control"
                                   placeholder="your@email.com" aria-describedby="emailHelp" required/>
                                <small id="emailHelp" className="form-text text-muted">
                                    ExpertPeople 는 사용자의 이메일을 공개하지 않습니다.
                                </small>
                                <small className="invalid-feedback">이메일을 입력하세요.</small>
                                <small className="form-text text-danger">Email Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="password">패스워드</label>
                            <input id="password" type="password"className="form-control"
                                   aria-describedby="passwordHelp" required minLength="8" maxLength="50"/>
                                <small id="passwordHelp" className="form-text text-muted">
                                    8자 이상 50자 이내로 입력하세요. 영문자, 숫자, 특수기호를 사용할 수 있으며 공백은 사용할 수 없습니다.
                                </small>
                                <small className="invalid-feedback">패스워드를 입력하세요.</small>
                                <small className="form-text text-danger">Password Error</small>
                        </div>

                        <div className="form-group py-1">
                            <label htmlFor="password-check">패스워드 확인</label>
                            <input id="password-check" type="password" className="form-control"
                                   aria-describedby="password-checkHelp" required minLength="8" maxLength="50"/>
                                <small id="password-checkHelp" className="form-text text-muted">

                                </small>
                                <small className="invalid-feedback">패스워드 확인을 입력하세요.</small>
                        </div>
                        <div className="form-group py-1">
                            <button type="submit" className="btn btn-primary">Submit</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default JoinUp;