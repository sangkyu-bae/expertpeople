import React from 'react';

function ServerError(props) {
    return (
    <>
        <small className="form-text text-danger">{props.serverErrorText}</small>
    </>
    );
}

export default ServerError;