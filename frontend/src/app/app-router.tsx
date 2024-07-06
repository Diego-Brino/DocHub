import {createBrowserRouter, Outlet, RouterProvider} from "react-router-dom";
import {LoginPage} from "../pages/login-page.tsx";
import {GroupsPage} from "@/pages/groups-page.tsx";
import {NotFoundPage} from "@/pages/not-found-page.tsx";

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
        path: "/",
        element: <Outlet/>,
        children: [
          {
            path: "/groups",
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