import { useState } from "react";
import { Link } from "react-router";
import { User, Mail, Lock, ImagePlus } from "lucide-react";
import { Button } from "../components/ui/button";
import { Input } from "../components/ui/input";
import { Label } from "../components/ui/label";
import { motion } from "motion/react";
import type RegisterData from "../Models/RegisterData";
import {
  Card,
  CardContent,
  CardFooter,
  CardDescription,
  CardHeader,
  CardTitle,
} from "../components/ui/card";
import toast from "react-hot-toast";
import { registerUser } from "../services/AuthServices";

export default function Register() {
  const [data, setData] = useState<RegisterData>({
    name: "",
    email: "",
    password: "",
  });

 const[loading, setLoading]= useState<boolean>(false);
 const[error,setError]=useState(null);

 //Now we bind the form data the values here 

 const handleInputChange=(e: React.ChangeEvent<HTMLInputElement>)=>{
     setData(value=>({
         ...value,
         [e.target.name]:e.target.value
     }));
 }  

 //handling formsubmit

 const handleFormSubmit=async(e:React.FormEvent)=>{
       console.log(import.meta.env.VITE_API_URL);
      //preventing default event behavior
       e.preventDefault();
       //adding validation 
       if(data.name.trim()==' '){
        toast.error("Name is required");
       } if(data.email.trim()==' '){
        toast.error("Email is required");
       } if(data.password.trim()==' '){
        toast.error("Password is required");
       }
      
       //using try and catch block
       try{
         const resp= await registerUser(data);
         console.log(resp);
         toast.success("User Registered Successfully!");
       }
       catch(error){
           toast.error("Some Error Occured");
       }
 };



  return (
     <motion.h1
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.8 }}
            className="text-5xl font-bold tracking-tight sm:text-6xl lg:text-7xl"
          >
    <div className="flex min-h-screen items-center justify-center bg-background px-4 py-10">
      <Card className="w-full max-w-md border-border bg-card shadow-xl">
        <CardHeader className="space-y-2 text-center">
          <div className="mx-auto mb-2 flex h-12 w-12 items-center justify-center rounded-2xl bg-primary/10">
            <User className="h-6 w-6 text-primary" />
          </div>

          <CardTitle className="text-2xl font-bold text-card-foreground">
            Create an account
          </CardTitle>

          <CardDescription>
            Register to get started with Auth01
          </CardDescription>
        </CardHeader>

        <CardContent>
          <form onSubmit={handleFormSubmit} className="space-y-5">
           {/* Name */}
            <div className="space-y-2">
              <Label htmlFor="name">Name</Label>
              <div className="relative">
                <User className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />

                <Input
                  id="name"
                  type="text"
                  placeholder="John Doe"                   
                  className="pl-10"
                  required
                  name="name"
                  value={data.name}
                  onChange={handleInputChange}
                />
              </div>
            </div>

            {/* Email */}
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>

              <div className="relative">
                <Mail className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />

                <Input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="john@example.com"
                  value={data.email}
                  onChange={handleInputChange}
                  className="pl-10"
                  required
                />
              </div>
            </div>

            {/* Password */}
            <div className="space-y-2">
              <Label htmlFor="password">Password</Label>

              <div className="relative">
                <Lock className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />

                <Input
                  id="password"
                  name="password"
                  type="password"
                  placeholder="••••••••"
                  value={data.password}
                  onChange={handleInputChange}
                  className="pl-10"
                  required
                />
              </div>
            </div>

            {/* Submit */}
            <Button type="submit" className="w-full">
              Create account
            </Button>

            {/* Login */}
             <CardFooter className="flex-col gap-2">
            <p className="text-center text-sm text-muted-foreground">
              Already have an account?{" "}
              <Link
                to="/login"
                className="font-medium text-primary hover:underline"
              >
                Login
              </Link>
               </p>
                 <Button variant="outline" className="w-full">
                  Login with Google
                </Button>
                <Button variant="outline" className="w-full">
                  Login with GitHub
                </Button>
            
            </CardFooter>
          </form>
        </CardContent>
      </Card>
    </div>
   </motion.h1>
  );

}