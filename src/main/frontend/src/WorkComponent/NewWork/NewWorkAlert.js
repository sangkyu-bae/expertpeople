import React from 'react';

function NewWorkAlert(props) {
    return (
        <div  className="alert alert-danger new-work" role="alert">
            <p>{props.text} </p>
            <p>
                확인 후 다시 입력하세요.
            </p>
        </div>
    );
}

export default NewWorkAlert;