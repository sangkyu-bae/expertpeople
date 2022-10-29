import React from 'react';

function RecruitAlertBox({text}) {
    return (
        <div  className="alert alert-danger new-work re-box" role="alert">
            <p>{text} </p>
        </div>
    );
}

export default RecruitAlertBox;