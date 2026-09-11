import type LoginData from "../Models/LoginData";
import type RegisterData from "../Models/RegisterData";
import apiClient from "../config/apiClient";

export const registerUser=async(signUpData : RegisterData)=>{

     const response= await apiClient.post('/auth/register', signUpData);
      console.log("REGISTER RESPONSE:", response);
     return response;
};

export const loginUser=async(signInData : LoginData)=>{

     const response= await apiClient.post('/auth/login', signInData);
      console.log("REGISTER RESPONSE:", response);
     return response;
};