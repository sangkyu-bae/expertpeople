import axios from "axios";

const API=axios.create({
    baseURL:"/",
  /*  headers:{
        access_token:cookies.get('access_token'),
    },*/
});

export default API;