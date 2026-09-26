import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import { BrowserRouter } from "react-router"
import {Routes, Route } from "react-router"
import App from './App.tsx'
import About from './pages/About.tsx'
import Login from './pages/Login.tsx'
import Register from './pages/Register.tsx'
import RootLayout from './pages/RootLayout.tsx'
import UserHome from './pages/user/UserHome.tsx'
import UserLayout from './pages/user/UserLayout.tsx'
import UserProfile from './pages/user/UserProfile.tsx'
import OAuthSuccessHandle from './pages/OAuthSuccessHandle.tsx'
import OAuthFailureHandle from './pages/OAuthFailureHandle.tsx'

createRoot(document.getElementById('root')!).render(
<BrowserRouter>
 <Routes>
    {/* Nested Routes */}
  <Route path="/" element={<RootLayout/>}>
  <Route index element={<App/>}/>
  <Route path="/about" element={<About/>}/>
  <Route path="/login" element={<Login/>}/>
  <Route path="/register" element={<Register/>}/>
  <Route path="/dashboard" element={<UserLayout/>}>
     <Route index element={<UserHome/>}/>
     <Route path="profile" element={<UserProfile/>}/>
  </Route>
  <Route path="/auth/success" element={<OAuthSuccessHandle/>}></Route>
  <Route path="/auth/failure" element={<OAuthFailureHandle/>}></Route>
  </Route>
 </Routes>
</BrowserRouter>,
)
