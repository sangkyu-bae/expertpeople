import React from 'react';
import {Alert} from "@mui/material";
import FormControl from "@mui/material/FormControl";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

function GetWorkNotification({myNotification,updateMyNotification}) {
    return (
        <div className="alram_box">
            <Alert severity="success" color="info">
                구인 신청 결과 알림을 받을 방법을 선택하세요.
            </Alert>
            <div>
                <FormControl component="fieldset"  sx={{ typography: 'subtitle2'  }} >
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={myNotification.workEnrollmentResultByEmail ?
                                <Switch color="primary" name="workEnrollmentResultByEmail"
                                        onChange={updateMyNotification}
                                        defaultChecked/> :
                                <Switch color="primary" name="workEnrollmentResultByEmail"
                                        onChange={updateMyNotification}/>
                            }
                            label="이메일로 받기"
                            labelPlacement="start"
                        />
                    </FormGroup>
                </FormControl>
                <FormControl component="fieldset">
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={myNotification.workEnrollmentResultByWeb ?
                                <Switch color="primary"  name="workEnrollmentResultByWeb"
                                        onChange={updateMyNotification}
                                        defaultChecked/> :
                                <Switch color="primary"  name="workEnrollmentResultByWeb"
                                        onChange={updateMyNotification}/>
                            }
                            label="웹으로 받기"
                            labelPlacement="start"
                        />
                    </FormGroup>
                </FormControl>
            </div>
        </div>
    );
}

export default GetWorkNotification;