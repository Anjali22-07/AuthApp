import React from "react";
import useAuth from "../../auth/store";

export default function UserHome(){
    
     const user= useAuth((state)=>state.user);
      const toTitleCase = (str: string) =>
       str
        .split(" ")                    // ["aarav", "jha"]
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))  // ["Aarav", "Jha"]
        .join(" ");  

    return(
        <>
         <div className="text-2xl py-1 font-bold">Welcome, {user?.name && toTitleCase(user.name)}</div>
         <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Consequuntur dolorem assumenda, quam exercitationem placeat fugiat vel adipisci eos dolor accusamus labore nobis est ab rerum id aliquam. Consectetur, corporis perferendis!</p>
   </> )
};