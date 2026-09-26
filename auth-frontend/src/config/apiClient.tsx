import axios from "axios";
import useAuth from "../auth/store";

const apiClient=axios.create({
    baseURL:import.meta.env.VITE_API_URL||"http://localhost:8080/api/V1",
    headers:{
       'Content-Type' :"application/json",
    },
    withCredentials:true,
    timeout:10000,

});

//for every client 
apiClient.interceptors.request.use((config) =>{
     console.log("🔥 INTERCEPTOR RUNNING");
   const token= useAuth.getState().accessToken;

   console.log(token);
   if(token){
    config.headers.Authorization=`Bearer ${token}`;
   }

   return config;
});

export default apiClient;