import * as React from 'react';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import PopupState, { bindTrigger, bindMenu } from 'material-ui-popup-state';

function Dropdown({workChangeSearch}) {
    return (
        <PopupState variant="popover" popupId="demo-popup-menu">
            {(popupState) => (
                <React.Fragment>
                    <Button variant="contained" {...bindTrigger(popupState)}>
                        정렬 방식
                    </Button>
                    <Menu {...bindMenu(popupState)}>
                        <MenuItem onClick={()=> {
                            popupState.close();
                          workChangeSearch('setSearchWithPublishDate');
                        }}>일감 공개일</MenuItem>
                        <MenuItem onClick={()=> {
                            popupState.close();
                            workChangeSearch('setSearchWithMemberLength');
                        }}>멤버수</MenuItem>
                    </Menu>
                </React.Fragment>
            )}
        </PopupState>
    );
}

export default Dropdown;