export const LOGIN_USER="LOGIN_USER";
export const LOGOUT_USER="LOGOUT_USER";

export const loginUser=(userData)=>({
    type:LOGIN_USER,
    user:{
        id:userData.id,
        name:userData.id,
        token:userData.token
    }
});
export const logoutUser=()=>({
    type:LOGOUT_USER,
    user:{
        id:'',
        name:'',
        token:''
    }
});

const initialState={
    user:{
        id:'',
        name:'',
        token:''
    }
}

export default function userReducer(state=initialState,action){
    switch (action.type){
        case LOGIN_USER:{
            return{
                ...state,
                user:action.user
            }
        }

        case LOGOUT_USER:{
            return {
                ...state,
                user:action.user
            }
        }

        default:
            return state
    }
}