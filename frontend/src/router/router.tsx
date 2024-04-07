import {createBrowserRouter} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login/login-page.tsx";
import {HomeLayout} from "@/layouts/home-layout.tsx";

const router = createBrowserRouter([
    {
      path: "/",
      element: <App/>,
      children: [
        {path: "login", element: <LoginPage/>},
        {
          path: "home",
          element: <HomeLayout/>,
          children: [

          ]
        },
      ]
    }
])

export default router;