import {Route, Routes, useLocation} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login-page.tsx";
import {AnimatePresence} from "framer-motion";
import {HomePage} from "@/pages/home-page.tsx";
import {FilesPage} from "@/pages/files-page.tsx";
import {GroupsPage} from "@/pages/groups-page.tsx";
import {FlowsPage} from "@/pages/flows-page.tsx";
import {PermissionsPage} from "@/pages/permissions-page.tsx";
import {MonitoringPage} from "@/pages/monitoring-page.tsx";
import {ProfilePage} from "@/pages/profile-page.tsx";
import {UsersPage} from "@/pages/users-page.tsx";
import {SettingsPage} from "@/pages/settings-page.tsx";

const Router = () => {
  const location = useLocation();

  return (
    <AnimatePresence mode="wait">
      <Routes location={location} key={location.pathname}>
        <Route path="" element={<App/>}>
          <Route path="login" element={<LoginPage/>}/>
          <Route path="home" element={<HomePage/>}/>
          <Route path="files" element={<FilesPage/>}/>
          <Route path="groups" element={<GroupsPage/>}/>
          <Route path="flows" element={<FlowsPage/>}/>
          <Route path="permissions" element={<PermissionsPage/>}/>
          <Route path="monitoring" element={<MonitoringPage/>}/>
          <Route path="profile" element={<ProfilePage/>}/>
          <Route path="users" element={<UsersPage/>}/>
          <Route path="settings" element={<SettingsPage/>}/>
        </Route>
      </Routes>
    </AnimatePresence>
  );
}

export default Router;