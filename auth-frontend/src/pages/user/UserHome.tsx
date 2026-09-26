
import { useEffect, useState } from "react";
import axios from "axios";
import {
  ShieldCheck,
  User,
  Mail,
  LogOut,
  LockKeyhole,
  CheckCircle2,
  AlertCircle,
} from "lucide-react";

import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "../../components/ui/card";

import { Button } from "../../components/ui/button";
import useAuth from "../../auth/store";

interface UserData {
  name: string;
  email: string;
  role: string;
  provider: string;
  enabled: boolean;
}

const UserHome = () => {
  const user= useAuth((state)=>state.user)
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const toTitleCase = (str: string) =>
       str
        .split(" ")                    // ["aarav", "jha"]
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))  // ["Aarav", "Jha"]
        .join(" ");

//   useEffect(() => {
//     const getUser = async () => {
//       try {
//         const response = await axios.get(
//           "http://localhost:8080/api/V1/user/me",
//           {
//             withCredentials: true,
//           }
//         );

//         setUser(response.data);
//       } catch (error) {
//         console.error(error);
//         setError("Unable to load user information.");
//       } finally {
//         setLoading(false);
//       }
//     };

//     getUser();
//   }, []);

//   const handleLogout = async () => {
//     try {
//       await axios.post(
//         "http://localhost:8080/api/V1/auth/logout",
//         {},
//         {
//           withCredentials: true,
//         }
//       );

//       window.location.href = "/login";
//     } catch (error) {
//       console.error("Logout failed:", error);
//     }
//   };

  // Loading state
  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-gray-900">
        <div className="text-center">
          <div className="mx-auto mb-4 h-8 w-8 animate-spin rounded-full border-4 border-slate-300 border-t-green-700" />

          <p className="text-sm text-slate-600">
            Loading your dashboard...
          </p>
        </div>
      </div>
    );
  }

  // Error state
  if (error || !user) {
    return (
      <div className="flex min-h-screen items-center justify-center bg-gray-900 px-6">
        <Card className="w-full max-w-md">
          <CardContent className="p-8 text-center">
            <AlertCircle className="mx-auto mb-4 h-10 w-10 text-red-500" />

            <h2 className="text-xl font-semibold">
              Unable to load dashboard
            </h2>

            <p className="mt-2 text-sm text-slate-500">
              {error || "User information was not found."}
            </p>

            <Button
              className="mt-6"
              onClick={() => (window.location.href = "/login")}
            >
              Go to Login
            </Button>
          </CardContent>
        </Card>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-950">
    

      {/* Main */}
      <main className="mx-auto max-w-7xl px-6 py-10">
        {/* Welcome */}
        <section className="mb-8">
          <p className="mb-2 text-sm font-medium text-green-700">
            USER DASHBOARD
          </p>

          <h1 className="text-3xl font-bold tracking-tight text-white sm:text-4xl">
            Welcome back, {user?.name && toTitleCase(user?.name)} 👋
          </h1>

          <p className="mt-2 text-slate-600">
            Manage your account and view your authentication status.
          </p>
        </section>

        {/* Stats */}
        <section className="mb-8 flex flex-wrap items-center justify-center gap-5">
          {/* Account Status */}
          <Card>
            <CardContent className="flex items-center gap-4 p-6">
              <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-green-100">
                <CheckCircle2 className="h-6 w-6 text-green-700" />
              </div>

              <div>
                <p className="text-sm text-slate-500">
                  Account Status
                </p>

                <p
                  className={`font-semibold ${
                    user.enabled
                      ? "text-green-700"
                      : "text-red-600"
                  }`}
                >
                  {user.enabled ? "Active" : "Disabled"}
                </p>
              </div>
            </CardContent>
          </Card>

      
          {/* <Card>
            <CardContent className="flex items-center gap-4 p-6">
              <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-blue-100">
                <User className="h-6 w-6 text-blue-700" />
              </div>

              <div>
                <p className="text-sm text-slate-500">
                  Role
                </p>

                <p className="font-semibold text-slate-900">
                  {user.}
                </p>
              </div>
            </CardContent>
          </Card> */}

          {/* Provider */}
          <Card>
            <CardContent className="flex items-center gap-4 p-6">
              <div className="flex h-12 w-12 items-center justify-center rounded-xl bg-purple-100">
                <LockKeyhole className="h-6 w-6 text-purple-700" />
              </div>

              <div>
                <p className="text-sm text-slate-500">
                  Auth Provider
                </p>

                <p className="font-semibold text-green-700">
                  {user.provider}
                </p>
              </div>
            </CardContent>
          </Card>
        </section>

        {/* Account Information */}
        <section className="grid gap-6 lg:grid-cols-2">
          <Card>
            <CardHeader>
              <CardTitle>Account Information</CardTitle>
            </CardHeader>

            <CardContent className="space-y-5">
              {/* Name */}
              <div className="flex items-center gap-4">
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-slate-100">
                  <User className="h-5 w-5 text-slate-600" />
                </div>

                <div>
                  <p className="text-xs text-slate-500">
                    Full Name
                  </p>

                  <p className="font-medium text-green-700">
                    {user.name && toTitleCase(user?.name)}
                  </p>
                </div>
              </div>

              {/* Email */}
              <div className="flex items-center gap-4">
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-slate-100">
                  <User className="h-5 w-5 text-slate-600" />
                </div>

                <div>
                  <p className="text-xs text-slate-500">
                    Email
                  </p>

              
                  <p className="font-medium text-green-700">
                    {user.email}
                  </p>
                </div>
              </div>

              {/* Role
              <div className="flex items-center gap-4">
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-slate-100">
                  <ShieldCheck className="h-5 w-5 text-slate-600" />
                </div> */}
{/* 
                <div>
                  <p className="text-xs text-slate-500">
                    Account Role
                  </p>

                  <p className="font-medium text-slate-900">
                    {user.role}
                  </p>
                </div>
              </div> */}

              {/* Provider */}
              <div className="flex items-center gap-4">
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-slate-100">
                  <LockKeyhole className="h-5 w-5 text-slate-600" />
                </div>

                <div>
                  <p className="text-xs text-slate-500">
                    Authentication Provider
                  </p>

                  <p className="font-medium text-green-700">
                    {user.provider}
                  </p>
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Security */}
          <Card>
            <CardHeader>
              <CardTitle>Security</CardTitle>
            </CardHeader>

            <CardContent>
              <div className="rounded-xl border bg-slate-50 p-5">
                <div className="mb-5 flex items-center gap-3">
                  <div className="flex h-10 w-10 items-center justify-center rounded-full bg-green-100">
                    <ShieldCheck className="h-5 w-5 text-green-700" />
                  </div>

                  <div>
                    <p className="font-semibold">
                      Account Protected
                    </p>

                    <p className="text-sm text-slate-500">
                      Your account is secured.
                    </p>
                  </div>
                </div>

                <div className="space-y-4 text-sm">
                  <div className="flex items-center justify-between">
                    <span className="text-slate-600">
                      JWT Authentication
                    </span>

                    <span className="font-medium text-green-700">
                      Enabled
                    </span>
                  </div>

                  <div className="flex items-center justify-between">
                    <span className="text-slate-600">
                      Refresh Token
                    </span>

                    <span className="font-medium text-green-700">
                      Active
                    </span>
                  </div>

                  <div className="flex items-center justify-between">
                    <span className="text-slate-600">
                      Account Status
                    </span>

                    <span
                      className={`font-medium ${
                        user.enabled
                          ? "text-green-700"
                          : "text-red-600"
                      }`}
                    >
                      {user.enabled ? "Active" : "Disabled"}
                    </span>
                  </div>

                  <div className="flex items-center justify-between">
                    <span className="text-slate-600">
                      Provider
                    </span>

                    <span className="font-medium text-green-700">
                      {user.provider}
                    </span>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </section>
      </main>
    </div>
  );
};

export default UserHome;
