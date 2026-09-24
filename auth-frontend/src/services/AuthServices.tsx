import type LoginData from "../Models/LoginData";
import type loginResponseData from "../Models/LoginResponseData";
import type RegisterData from "../Models/RegisterData";
import apiClient from "../config/apiClient";

export const registerUser=async(signUpData : RegisterData)=>{

     const response= await apiClient.post('/auth/register', signUpData);
      console.log("REGISTER RESPONSE:", response);
     return response;
};

export const loginUser=async(signInData : LoginData)=>{

     const response= await apiClient.post<loginResponseData>('/auth/login', signInData);
      console.log("REGISTER RESPONSE:", response);
     return response;
};

export const logoutUser=async ()=>{
     const resp= await apiClient.post('/auth/logout');
     return resp;
}