import {Route, Routes, useLocation} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login-page.tsx";
import {GroupsPage} from "@/pages/groups-page.tsx";
import {NotFoundPage} from "@/pages/not-found-page.tsx";

const Router = () => {
  const location = useLocation();

  return (
    <Routes location={location} key={location.pathname}>
      <Route path="/" element={<App/>}>
        {["/", "login"].map(path => (
            <Route path={path} element={<LoginPage/>}/>
        ))}
        <Route path="login" element={<LoginPage/>}/>
        <Route path="groups" element={<GroupsPage/>}/>
        <Route path='*' element={<NotFoundPage/>} />
      </Route>
    </Routes>
  );
}

export default Router;