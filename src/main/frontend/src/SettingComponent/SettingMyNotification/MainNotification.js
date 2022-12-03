import React from 'react';
import {Alert} from "@mui/material";
import FormControl from "@mui/material/FormControl";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

function MainNotification({myNotification,updateMyNotification}) {
    return (
        <div className="alram_box">
            <Alert severity="success" color="info">
                주요 구직 지역에 관심있는 일감 및 구인 시작시 알림을 받을 방법을 선택하세요.
            </Alert>
            <div>
                <FormControl component="fieldset" sx={{typography: 'subtitle2'}}>
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={myNotification.workCreateByEmail ?
                                <Switch color="primary" name="workCreateByEmail"
                                        onChange={updateMyNotification}
                                        defaultChecked/> :
                                <Switch color="primary" name="workCreateByEmail"
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
                            control={myNotification.workCreateByWeb ?
                                <Switch color="primary" name="workCreateByWeb"
                                        onChange={updateMyNotification}
                                        defaultChecked/> :
                                <Switch color="primary" name="workCreateByWeb"
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

export default MainNotification;