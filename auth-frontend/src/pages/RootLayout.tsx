import { Outlet } from "react-router";
import NavBar  from "../components/NavBar"
function RootLayout(){

    return(<>
       <div>
        <NavBar/>
        <Outlet/>
       </div>
    </>);
}

export default RootLayout;