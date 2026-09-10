import { Outlet } from "react-router";
import NavBar  from "../components/NavBar"
import {Toaster} from "react-hot-toast"
function RootLayout(){

    return(<>
       <div>
       <Toaster></Toaster>
        <NavBar/>
        <Outlet/>
       </div>
    </>);
}

export default RootLayout;