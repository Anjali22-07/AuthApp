import {create} from "zustand";
import type User from "../Models/User";
import type LoginData from "../Models/LoginData";
import { loginUser, logoutUser } from "../services/AuthServices";
import type loginResponseData from "../Models/LoginResponseData";
import {persist} from 'zustand/middleware';
 
 const Token_Key="auth_app";

 //global state
 type AuthState={

     accessToken:string | null,
     user: User | null,
     setUser: (user: User) => void,
     authStatus: boolean,
     authLoading :boolean,

     login: (loginData : LoginData)=>void;
     logout:(options?:{silent?:boolean}) => void;
     checkLogin:()=> boolean | undefined;
 }

 // main logic for global state
 const useAuth= create<AuthState>()(persist((set,get)=>({
     accessToken:null,
     user : null,
     authLoading:false,
     authStatus:false,

    setUser: (user) => set({ user }),


     login: async (loginData)=>{
        console.log("Login started");
        set({authLoading:true});
        const responseData= await loginUser(loginData);
        console.log(responseData);
        set({
            accessToken:responseData.data.accessToken,
            user: responseData.data.userDto,
            authStatus:true,
        })
     },

    logout:async (silent)=>{
    try {
    await logoutUser();
  } catch (error) {
    console.error("Logout API failed:", error);
  } finally {
    set({
      accessToken: null,
      user: null,
      authStatus: false,
      authLoading: false,
    });
}       
         
     },

     checkLogin:()=>{
        if(get().accessToken && get().authStatus) return true;
        else return false;
     }
}),{
    name: Token_Key,
}
 )
);
      
export default useAuth;
 
  