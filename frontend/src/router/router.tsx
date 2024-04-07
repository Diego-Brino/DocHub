import {Route, Routes, useLocation} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login/login-page.tsx";
import {HomeLayout} from "@/layouts/home-layout.tsx";

const Router = () => {
  const location = useLocation();

  return (
    <Routes location={location} key={location.pathname}>
      <Route path="/" element={<App/>}>
        <Route path="login" element={<LoginPage/>}/>
        <Route path="home/*" element={<HomeLayout/>}/>
      </Route>
    </Routes>
  );
}

export default Router;