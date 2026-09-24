import React, { use } from "react";
import { Button } from "./ui/button";
import { NavLink, useNavigate } from "react-router";
import useAuth from "../auth/store";
 
function NavBar(){

      const authstatus= useAuth((state)=>state.authStatus);
      const logout=useAuth((state)=>state.logout);
      const user= useAuth((state)=>state.user)
      const navigate=useNavigate();
      const toTitleCase = (str: string) =>
       str
        .split(" ")                    // ["aarav", "jha"]
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))  // ["Aarav", "Jha"]
        .join(" ");    

    return(
      <nav className="md:py-2 py-2 flex md:flex-row justify-around text-center md:gap-0 gap-4 border-b border-gray-200 dark:border-gray-600 h-10 ">
        <div className="flex text-center gap-2">
            <span className="inline-block text-center h-6 w-6 rounded-md bg-gradient-to-r from-primary to-primary/40">
               A
            </span>
          <NavLink to="/"><span className="text-base tracking-tight">Auth01</span></NavLink>
        </div>
        {/* Menu */}
        <div className="flex text-center gap-4"> 
         { authstatus?   
         <>
          <a href="/" className="text-sm py-1 font-bold">{user?.name  && toTitleCase(user.name)}</a>
            <Button onClick={()=>{logout(); 
            navigate("/login");}}
            size={"sm"} className= "cursor-pointer"  variant={"outline"}>Logout</Button>
             </>
            : 
             <> <a href="/" className="text-sm py-1">Home</a>
            <NavLink to="/login">
                <Button size={"sm"} className= "cursor-pointer" variant={"outline"} >Login</Button>
            </NavLink>
             <NavLink to="/register">
                <Button size={"sm"} className= "cursor-pointer"  variant={"outline"}>SignUp</Button>
             </NavLink>
             </>
}
        </div>
      </nav>
    );
}

export default NavBar;