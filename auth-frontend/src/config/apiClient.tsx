import axios from "axios";
import useAuth from "../auth/store";
import { getRefreshToken } from "../services/AuthServices";

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


let isRefreshing = false;
let pending:any []=[];

function queueRequest(cb:any){
   pending.push(cb);
}

function resolveRequest(newToken:string){
    pending.forEach((cb)=>cb(newToken))
}


apiClient.interceptors.response.use((response)=> response,
async (error)=>{
   const is401= error.response.status===401;
   const original=error.config;
   if(!is401 || original._retry)  return Promise.reject(error);

   //we will refresh thetoken 
   if(isRefreshing){
      return new Promise((resolve,reject)=>{
          queueRequest((newToken:string)=>{
            if(!newToken)  return reject();
            original.headers.Authorization=`Bearer ${newToken}`;
            resolve(apiClient(original));
          });
      })
   };

   //start Refreshing-- in AuthService there's a method for refresh API call
   isRefreshing=true;
   try{
      const loginResponseData= await getRefreshToken();
       const newToken= loginResponseData.data.accessToken;
       if(!newToken) throw new Error("no Access Token Recieved");
       useAuth.getState().changeLoginData(loginResponseData.data.accessToken, loginResponseData.data.userDto, true);
       resolveRequest(newToken);
       original.headers.Authorization=`Bearer ${newToken}`;
       return apiClient(original);
   }catch(error){
      return Promise.reject(error);
   }
})


export default apiClient;