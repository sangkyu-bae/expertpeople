import React from 'react';
import Grid from "@mui/system/Unstable_Grid";
import MainCenterCard from "./MainCenterCard";
import Stack from "@mui/material/Stack";
import Alert from "@mui/material/Alert";

function MainCenterUpComponent({recruitments}) {
    return (
        <>
            <Grid xs={12}>
                <div className="main_head">참석 일감 목록</div>
            </Grid>
            {
                recruitments.length>0?
                    recruitments.map(recruitment=>
                        <MainCenterCard
                            key={recruitment.id}
                            recruitment={recruitment}></MainCenterCard>
                    )
                    :
                    <Stack sx={{ width: '100%' }} spacing={2}>
                        <Alert severity="info">참석할 일감이 없습니다.</Alert>
                    </Stack>
            }
        </>

    );
}

export default MainCenterUpComponent;