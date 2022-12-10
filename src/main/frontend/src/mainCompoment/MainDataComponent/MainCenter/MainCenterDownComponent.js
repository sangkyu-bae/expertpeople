import React, {useEffect} from 'react';
import ActionAreaCard from "../../../CommonComponent/ActionAreaCard";
import Grid from "@mui/system/Unstable_Grid";
import './mainCenter.css';

function MainCenterDownComponent({interestWorks}) {
    useEffect(()=>{
        const a=document.querySelector(".container");
        a.style.width="95%";
    },[])

    return (
      <>
          <Grid xs={12}>
              <div className="main_head">주요 구직 지역의 관심 일감</div>
          </Grid>
          {
              interestWorks.length>0&&
              interestWorks.map(interestWork=>
                  <Grid xs={4}>
                      <ActionAreaCard key={interestWork.id} search={interestWork}></ActionAreaCard>
                  </Grid>
              )
          }

      </>
    );
}

export default MainCenterDownComponent;