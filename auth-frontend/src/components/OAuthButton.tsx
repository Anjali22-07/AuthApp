import react from "react";
import { Button } from "./ui/button";
import { NavLink } from "react-router";

function OAuthButton(){
    return(
        <>
        <div className="space-y-3">
       <NavLink to={`${import.meta.env.VITE_BASE_URL|| "http://localhost:8080"}/oauth2/authorization/google`} className={"block"}>
         <Button 
           type="button" 
           variant="outline"  className="w-full cursor-pointer">
          Login with Google
        </Button>
       </NavLink>
       <NavLink to={`${import.meta.env.VITE_BASE_URL|| "http://localhost:8080"}/oauth2/authorization/github`} className={"block"}>
        <Button 
        type="button" 
        variant="outline" className="w-full cursor-pointer ">
          Login with GitHub
        </Button>
       </NavLink>

</div></>
    )
}

export default OAuthButton;