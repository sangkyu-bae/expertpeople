import React, {useEffect, useState} from 'react';
import Card from "@mui/material/Card";
import {CardActionArea, Chip} from "@mui/material";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import {Marker} from "react-mark.js";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import EngineeringIcon from "@mui/icons-material/Engineering";
import {useLocation} from "react-router-dom";

function MainCardView({search, keyword}) {
    const [cardWidth,setCardWidth]=useState([]);
    const location=useLocation();

    const width={
        small:[280,280],
        big:[345,345]
    }
    const short=['/myprofile/admin-work'];
    const long=['/']
    useEffect(()=>{
        if(short.includes(location.pathname)){
            setCardWidth(width.small);
        }

        if(long.includes(location.pathname)){
            setCardWidth(width.big);
        }
    },[])


    return (
        // <Card sx={`${locationPath.includes("profile")&&{maxWidth: 280, minWidth: 280}} `} className="card_box">
        <Card sx={{maxWidth: cardWidth[0], minWidth: cardWidth[1]}} className="card_box">
            <CardActionArea>
                <CardMedia
                    component="img"
                    height="40"
                    image="/static/images/cards/contemplative-reptile.jpg"
                    alt="green iguana"
                />
                <CardContent sx={{minHeight: 180}}>
                    <Typography gutterBottom variant="h5" component="div" className='card_head'>
                        {search.title}
                    </Typography>
                    <Typography variant="body2" variant="h6" color="text.secondary" className="short_box">
                        {search.shortDescription}
                    </Typography>
                    <div className="card_tag_list">
                        {
                            search.zones.length > 0 &&
                            search.zones.map((zone) =>
                                <Marker mark={keyword} key={zone.id} options={{className: "custom-marker-1"}}>
                                    <Chip icon={<LocationOnIcon/>} label={zone.localNameOfCity}
                                          className="ee"/>
                                </Marker>
                            )
                        }
                        {
                            search.jobs.length > 0 &&
                            search.jobs.map((job) =>
                                <Marker mark={keyword} key={job.id} options={{className: "custom-marker-1"}}>
                                    <Chip icon={<EngineeringIcon/>} label={job.job} variant="outlined"
                                          className="ee"/>
                                </Marker>
                            )
                        }
                    </div>
                    <div className="card_foot">
                        <div className="card_member">{search.members.length}명</div>
                        <div className="card_date">{search.publishedDateTime}</div>
                    </div>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}

export default MainCardView;