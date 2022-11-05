import React from 'react';
import './Index.css'
import {useSelector} from "react-redux";
import axiosCo from "../util/common/axiosCommon";

function Index(props) {
    const login=useSelector(state=> state.userReducer.user);
    // console.log(login);
    // const te=axiosCo.test()
    //     .then(e=>console.log(e))
    //     .catch(e=>console.log(e))

    return (
        <div>
            <div className="container">
                <div className="py-5 text-center">
                    <h2> 전문인력들을 만나다 ExpertPeople</h2>
                </div>
            </div>
        </div>
    );
}

export default Index;