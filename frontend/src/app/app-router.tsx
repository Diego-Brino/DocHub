import {createBrowserRouter, Navigate, RouterProvider, useNavigate} from "react-router-dom";
import {LoginPage} from "../pages/login-page.tsx";
import {GroupsPage} from "@/pages/groups-page.tsx";
import {NotFoundPage} from "@/pages/not-found-page.tsx";
import {Main} from "@/layouts/main";
import {ResetPasswordPage} from "@/pages/reset-password-page.tsx";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import {ReactNode, useEffect} from "react";

const AuthenticatedRoute = ({children}: {children: ReactNode}) => {
  const {token} = useAuthContext()
  return token ? children : <Navigate to="/login"/>
}

const UnauthenticatedRoute = ({children}: {children: ReactNode}) => {
  const {token} = useAuthContext()
  const navigate = useNavigate()

  useEffect(() => {
    if (token) {
      navigate(-1)
    }
  }, [navigate, token]);

  return !token ? children : null
}

const AppBrowserRouter = createBrowserRouter([
  {
    path: "/",
    children: [
      {
        path: "/",
        element: (
          <UnauthenticatedRoute>
            <LoginPage/>
          </UnauthenticatedRoute>
        )
      },
      {
        path: "/login",
        element: (
          <UnauthenticatedRoute>
            <LoginPage/>
          </UnauthenticatedRoute>
        )
      },
      {
        path: "/reset-password",
        element: (
          <UnauthenticatedRoute>
            <ResetPasswordPage/>
          </UnauthenticatedRoute>
        )
      },
      {
        path: "/",
        element: (
          <AuthenticatedRoute>
            <Main/>
          </AuthenticatedRoute>
        ),
        children: [
          {
            path: "/groups",
            element: (
              <AuthenticatedRoute>
                <GroupsPage/>
              </AuthenticatedRoute>
            )
          },
          {
            path: "/users",
            element: (
              <AuthenticatedRoute>
                <GroupsPage/>
              </AuthenticatedRoute>
            )
          },
          {
            path: "/roles",
            element: (
              <AuthenticatedRoute>
                <GroupsPage/>
              </AuthenticatedRoute>
            )
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