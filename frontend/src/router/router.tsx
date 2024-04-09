import {Route, Routes, useLocation} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login/login-page.tsx";
import {AnimatePresence} from "framer-motion";
import {HomePage} from "@/pages/home/home-page.tsx";

const Router = () => {
  const location = useLocation();

  return (
    <AnimatePresence mode="sync">
      <Routes location={location} key={location.pathname}>
        <Route path="/" element={<App/>}>
          <Route path="login" element={<LoginPage/>}/>
          <Route path="home" element={<HomePage/>}/>
        </Route>
      </Routes>
    </AnimatePresence>
  );
}

export default Router;