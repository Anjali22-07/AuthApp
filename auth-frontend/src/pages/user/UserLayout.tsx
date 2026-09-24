import React from "react";
import { Outlet, Navigate } from "react-router";
import useAuth from "../../auth/store";

export default function UserLayout(){

   const checkLogin=useAuth((state)=>state.checkLogin);
    
   if(checkLogin())
     return(
        <div>
            <Outlet/>
        </div>
     );
   else return <Navigate to={"/login"}/>

 
};