import React from 'react';
import {Alert} from "@mui/material";
import FormControl from "@mui/material/FormControl";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

function InterestWorkNotification({myNotification,updateMyNotification}) {
    return (
        <div className="alram_box">
            <Alert severity="success" color="info">
                 일감에 변동사항에 대한 알림을 받을 방법을 설정하세요.
            </Alert>
            <div>
                <FormControl component="fieldset"  sx={{ typography: 'subtitle2'  }} >
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={myNotification.workUpdateByEmail ?
                                <Switch color="primary" name="workUpdateByEmail"
                                        onChange={updateMyNotification}
                                        defaultChecked /> :
                                <Switch color="primary" name="workUpdateByEmail"
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
                            control={myNotification.workUpdateByWeb ?
                                <Switch color="primary" name="workUpdateByWeb"
                                        onChange={updateMyNotification}
                                        defaultChecked/> :
                                <Switch color="primary" name="workUpdateByWeb"
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

export default InterestWorkNotification;