import {createBrowserRouter} from "react-router-dom";
import App from "../App.tsx";
import {LoginPage} from "../pages/login-page.tsx";

const router = createBrowserRouter([
    {
      path: "/",
      element: <App/>,
      children: [
        {path: "login", element: <LoginPage/>},
      ]
    }
])

export default router;