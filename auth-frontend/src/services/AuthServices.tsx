import type RegisterData from "../Models/RegisterData";
import apiClient from "../config/apiClient";

export const registerUser=async(signUpData : RegisterData)=>{

     const response= await apiClient.post('/auth/register', signUpData);
     return response;
};