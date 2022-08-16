export const LOGIN_USER="LOGIN_USER";
export const LOGOUT_USER="LOGOUT_USER";

export const loginUser=(userData)=>({
    type:LOGIN_USER,
    user:{
        id:userData.id,
        name:userData.name,
        token:userData.token
    },
    isLogin:true
});
export const logoutUser=()=>({
    type:LOGOUT_USER,
    user:{
        id:'',
        name:'',
        token:''
    },
    isLogin: false
});

const initialState={
    user:{
        id:'',
        name:'',
        token:''
    },
    isLogin:false
}

export default function userReducer(state=initialState,action){
    switch (action.type){
        case LOGIN_USER:{
            return{
                ...state,
                user:action.user,
                isLogin: action.isLogin
            }
        }

        case LOGOUT_USER:{
            return {
                ...state,
                user:action.user,
                isLogin: action.isLogin
            }
        }

        default:
            return state
    }
}