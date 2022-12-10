import React from 'react';
import Grid from "@mui/system/Unstable_Grid";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CalendarTodayIcon from "@mui/icons-material/CalendarToday";
import CardActions from "@mui/material/CardActions";
import Button from "@mui/material/Button";
import {Link} from "react-router-dom";
import moment from "moment/moment";

function MainCenterCard({recruitment}) {
    recruitment.createTime = moment(recruitment.createTime).format("YYYY-MM-DD ");
    return (
        <Grid xs={6}>
            <Card>
                <CardContent>
                    <Typography variant="h5"   gutterBottom>
                        {recruitment.title}
                    </Typography>
                    <Typography  sx={{ fontSize: 16  }}   color="text.secondary" component="div">
                        {recruitment.workDescription}
                    </Typography>
                    <div>
                        <CalendarTodayIcon fontSize="small"/>
                        <Typography component="span" sx={{ md: 1.5}} color="text.secondary">
                            {recruitment.createTime}
                        </Typography>
                    </div>

                </CardContent>
                <CardActions sx={{md:5}}>
                    <Link to={`/work/${recruitment.workPath}/recruitment/${recruitment.recruitId}`}>
                        <Button size="small">구인 조회</Button>
                    </Link>
                    <Link to={`/work/${recruitment.workPath}`}>
                        <Button size="small">일감 조회</Button>
                    </Link>
                </CardActions>
            </Card>
        </Grid>
    );
}

export default MainCenterCard;
