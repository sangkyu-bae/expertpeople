import React from 'react';

function Error(props) {

    return (
        <>
            <small className="invalid-feedback">{props.nullText}</small>
        </>
    );
}

export default Error;