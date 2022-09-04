import React, {useState} from 'react';
import axiosCo from "../../util/common/axiosCommon";

function PasswordCenter(props) {
    const [inputs,setInputs]=useState({
        password:"",
        newPassword:""
    })
    const {password,newPassword}=inputs;
    const passwordValidation =()=>{
        if(inputs.password!=inputs.newPassword) return false;
        if(inputs.password.length<8) return false;
        return true
    }
    const onChange=e=>{
        const{name,value}=e.target;
        setInputs(
            {
                ...inputs,
                [name]:value
            }
        );
    }
    const onSubmit=e=>{
        e.preventDefault();
        const check=passwordValidation();

        if(!check) {
            props.checkPassword();
            return false;
        }
        axiosCo.updatePassword(inputs)
            .then(e=>success())
            .catch(e=>console.log(e))
    }

    const success =()=>{
        props.changeCheckOk();
        setInputs(
            {
                password:"",
                newPassword:""
            }
        )
    }
    return (
        <div className="password-head">
            <form className="password-form" onSubmit={onSubmit}>
                <div className="password-head-text">패스워드 변경</div>
                <div className="password-content">
                    <div className="new-password-box">
                        <div className="content-head">새 패스워드</div>
                        <input className='password-box' name="password" onChange={onChange} type="password" value={password}/>
                        <small>새 패스워드를 입력하세요.</small>
                    </div>
                    <div className="new-password-check">
                        <div className="content-head">새 패스워드 확인</div>
                        <input className="password-box" name="newPassword"onChange={onChange} type="password" value={newPassword}/>
                        <small>새 패스워드를 다시 한번 입력하세요.</small>
                    </div>
                </div>
                <button className='password-btn'>패스워드 변경하기</button>
            </form>

        </div>
    );
}

export default PasswordCenter;
