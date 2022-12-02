import React from 'react';
import {Alert} from "@mui/material";
import FormControl from "@mui/material/FormControl";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

function InterestWorkNotification(props) {
    return (
        <div className="alram_box">
            <Alert severity="success" color="info">
                관심있는 일감에 대한 알림을 받을 방법을 설정하세요.
            </Alert>
            <div>
                <FormControl component="fieldset"  sx={{ typography: 'subtitle2'  }} >
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={<Switch color="primary" />}
                            label="이메일로 받기"
                            labelPlacement="start"
                        />
                    </FormGroup>
                </FormControl>
                <FormControl component="fieldset">
                    <FormGroup aria-label="position" row>
                        <FormControlLabel
                            value="start"
                            control={<Switch color="primary" />}
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