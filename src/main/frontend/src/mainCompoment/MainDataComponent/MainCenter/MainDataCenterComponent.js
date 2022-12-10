import React, {useEffect} from 'react';
import Box from "@mui/system/Box";
import Grid from "@mui/system/Unstable_Grid";
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import MainCenterCard from "./MainCenterCard";
import MainCenterUpComponent from "./MainCenterUpComponent";
import MainCenterDownComponent from "./MainCenterDownComponent";
function MainDataCenterComponent({recruitments,interestWorks}) {

    return (
        <div className="center-content">
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2}>
                    <MainCenterUpComponent recruitments={recruitments}></MainCenterUpComponent>
                    <MainCenterDownComponent interestWorks={interestWorks}></MainCenterDownComponent>
                </Grid>
            </Box>
        </div>
    );
}

export default MainDataCenterComponent;