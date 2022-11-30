import React from 'react';
import JoinupHead from "./JoinupHead";
import {Alert, Box} from "@mui/material";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";

function LoginRoute({isLogin, component}) {
    return (
        isLogin ? component :
            <>
                <Box sx={{width: '100%'}}>
                    <Alert color="warning">로그인후 접근하세요.</Alert>
                </Box>
                <JoinupHead></JoinupHead>
            </>

    );
}

export default LoginRoute;