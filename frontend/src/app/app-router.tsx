import {createBrowserRouter, RouterProvider} from "react-router-dom";
import {LoginPage} from "../pages/login-page.tsx";
import {GroupsPage} from "@/pages/groups-page.tsx";
import {NotFoundPage} from "@/pages/not-found-page.tsx";
import {Main} from "@/layouts/main";
import {ResetPasswordPage} from "@/pages/reset-password-page.tsx";

const AppBrowserRouter = createBrowserRouter([
  {
    path: "/",
    children: [
      {
        path: "/",
        element: <LoginPage/>
      },
      {
        path: "/login",
        element: <LoginPage/>
      },
      {
        path: "/reset-password",
        element: <ResetPasswordPage/>
      },
      {
        path: "/",
        element: <Main/>,
        children: [
          {
            path: "/groups",
            element: <GroupsPage/>
          },
          {
            path: "/users",
            element: <GroupsPage/>
          },
          {
            path: "/roles",
            element: <GroupsPage/>
          }
        ]
      },
      {
        path: "*",
        element: <NotFoundPage/>
      },
    ]
  }
])

function AppRouter(){
  return (
    <RouterProvider router={AppBrowserRouter}/>
  )
}

export default AppRouter