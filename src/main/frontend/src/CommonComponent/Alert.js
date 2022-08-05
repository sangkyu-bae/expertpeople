import React, {useEffect} from 'react';

function Alert(props) {


    return (
        <small id="emailHelp" className="form-text text-muted">
            {props.text}
        </small>
    );
}

export default Alert;