import { Card, 
  CardAction,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle, } from '../components/ui/card'
import { Button } from "../components/ui/button"
import { Label } from "../components/ui/label"
import { Input } from "../components/ui/input"
import { NavLink, useNavigate } from 'react-router';
import { motion } from "motion/react";
import { useState } from 'react';
import type LoginData from '../Models/LoginData';
import toast from 'react-hot-toast';
import { loginUser } from '../services/AuthServices';
import { Alert, AlertTitle } from '../components/ui/alert';
import { Check, CheckCircle2Icon } from 'lucide-react';
import { Spinner } from '../components/ui/spinner';
import useAuth from '../auth/store';




function Login(){

  const[logindata, setLogindata]=useState<LoginData>({
      email:"",
      password:"",

  });

  const[loading, setLoading]=useState<boolean>(false);
  const[error, setError]=useState<any>(null);

  //now we will bind the form data 
  const  handleInputChange=(e:React.ChangeEvent<HTMLInputElement>)=>{
    setLogindata(()=>({
       ...logindata,
       [e.target.name]:e.target.value
    }));
  };

   const login =useAuth((state)=>state.login)
   const navigate= useNavigate();
  //handling form submit 
  const handleFormSubmit=async (e:React.FormEvent)=>{
    //preventing the default behavior
     e.preventDefault();
     console.log(logindata);

     //validation 
      if(logindata.email.trim()===""){
        toast.error("Email is Reuqired");
      }
      if(logindata.password.trim()===""){
        toast.error("password is required");
      }
    
      //form submitted
      try{
        setLoading(true);
      //   const resp= await loginUser(logindata);
       //  console.log(resp);
       await login(logindata);
         toast.success("You are Logged in");
          navigate("/dashboard");
         //save the current userLoggedIn information
         //we save the user information in localStroage while the user is logged in   
      }
      catch(error:any){
          console.log(error);
          toast.error("Error!")
          if(error?.status===400){
          setError(error);
          }else{
            setError(error)
          }
      }finally{
        setLoading(false);
      }
       

  }
return(
    <>
    <motion.h1
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-5xl font-bold tracking-tight sm:text-6xl lg:text-7xl"
          >
    <div className="flex justify-center mt-20">
        <Card className="w-full max-w-sm">
      <CardHeader>
        <CardTitle>Login to your account</CardTitle>
        <CardDescription>
          Enter your email below to login to your account
          {/* error section */}
          {
            error && (
              <div className="mt-4 ml-4 text-center items-center">
                <Alert variant={'destructive'}>
                  <CheckCircle2Icon/>
                    <AlertTitle>
                     { error?.response ? error?.response?.data?.message :
                      error?.message}
                    </AlertTitle>
                </Alert>
              </div>
            )
          }
        </CardDescription>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleFormSubmit}>
          <div className="flex flex-col gap-6">
            <div className="grid gap-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                placeholder="m@example.com"
                required
                name="email"
                value={logindata.email}
                onChange={handleInputChange}
              />
            </div>
            <div className="grid gap-2">
              <div className="flex items-center">
                <Label htmlFor="password">Password</Label>
                <a
                  href="#"
                  className="ml-auto inline-block text-sm underline-offset-4 hover:underline"
                >
                  Forgot your password?
                </a>
              </div>
              <Input id="password" 
              type="password"
               required
                name="password"
                value={logindata.password}
                onChange={handleInputChange}
                 />
            </div>
          </div>
          <CardFooter className="flex-col gap-2 mt-2">
        <Button disabled={loading} type="submit" className="w-full">
          {loading ? (<><Spinner/>Please Wait...</>)
          :("Login") }
        </Button>
        <Button variant="outline" className="w-full">
          Login with Google
        </Button>
         <Button variant="outline" className="w-full">
          Login with GitHub
        </Button>
         <CardAction>
         <p className="ml-16"> Do not have an Account?
          <NavLink to="/register"><Button size={"sm"} className= "cursor-pointer ml-20"  variant={"outline"}>SignUp</Button></NavLink>
        </p></CardAction>
      </CardFooter>
        </form>
      </CardContent>
      </Card>
    </div>
    </motion.h1>
    </>
    
);
}
export default Login;