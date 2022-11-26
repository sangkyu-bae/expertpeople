import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import {CardActionArea, Chip} from '@mui/material';
import './Custom.css';
import moment from "moment";
import LocationOnIcon from '@mui/icons-material/LocationOn';
import EngineeringIcon from '@mui/icons-material/Engineering';
import {Link} from "react-router-dom";
import Markdown from "react-mark/lib/components/basic/Markdown";
function ActionAreaCard({search}) {
    search.publishedDateTime = moment(search.publishedDateTime).format("YYYY-MM-DD ");
    return (
        <Link to={`work/${search.path}`} className='card_box'>
            <Card sx={{maxWidth: 345,minWidth: 345}} className="card_box">
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
                                        <Chip icon={<LocationOnIcon/>} label={zone.localNameOfCity} className="ee"/>
                                )
                            }
                            {
                                search.jobs.length > 0 &&
                                search.jobs.map((job) =>
                                    <Chip icon={<EngineeringIcon/>} label={job.job} variant="outlined" className="ee"/>
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
        </Link>


    );
}

export default ActionAreaCard;