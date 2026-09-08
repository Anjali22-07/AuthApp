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

createRoot(document.getElementById('root')!).render(
<BrowserRouter>
 <Routes>
    {/* Nested Routes */}
  <Route path="/" element={<RootLayout/>}>
  <Route index element={<App/>}/>
  <Route path="/about" element={<About/>}/>
  <Route path="/login" element={<Login/>}/>
  <Route path="/register" element={<Register/>}/>
  </Route>
 </Routes>
</BrowserRouter>,
)
