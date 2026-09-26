import React, { useEffect, useState } from "react";
import useAuth from "../auth/store";
import { getRefreshToken } from "../services/AuthServices";
import toast from "react-hot-toast";
import { useNavigate } from "react-router";
import { Spinner } from "../components/ui/spinner";

function OAuthSuccessHandle(){

    const [isRefreshing,setIsRefreshing]= useState<boolean>(false);
    const changeLoginDetails= useAuth((state)=>state.changeLoginData);
    const navigate= useNavigate();
    useEffect(()=>{
         
          async function getAccesToken(){
            if(!isRefreshing){
            setIsRefreshing(true);
            try{
                const responseData= await getRefreshToken();

                changeLoginDetails(
                    responseData.data.accessToken,
                    responseData.data.userDto,
                    true
                );
                toast.success("You are Logged in")
                navigate("/dashboard")
            }catch(error){
                toast.error("Some Error Occured");
                console.log(error);
            }finally{
                setIsRefreshing(false);
            }
         }
          }

          getAccesToken();
    });
    return(
        <div>
            <Spinner>Loading...please wait</Spinner>
        </div>
    )
}

export default OAuthSuccessHandle;