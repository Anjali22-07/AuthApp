import React from "react";
import { Button } from "./ui/button";
import { NavLink } from "react-router";
 
function NavBar(){
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
            <a href="/" className="text-sm py-1">Home</a>
            <NavLink to="/login">
                <Button size={"sm"} className= "cursor-pointer" variant={"outline"} >Login</Button>
            </NavLink>
             <NavLink to="/register">
                <Button size={"sm"} className= "cursor-pointer"  variant={"outline"}>SignUp</Button>
             </NavLink>
        </div>
      </nav>
    );
}

export default NavBar;