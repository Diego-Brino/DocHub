import {
  createBrowserRouter,
  Navigate,
  RouterProvider,
  useNavigate,
} from "react-router-dom";
import { Login } from "../pages/login.tsx";
import { Groups } from "@/pages/groups.tsx";
import { NotFound } from "@/pages/not-found.tsx";
import { Main } from "@/layouts/main";
import { ResetPassword } from "@/pages/reset-password.tsx";
import { ReactNode, useEffect } from "react";
import { useAuthContext } from "@/contexts/auth";
import { Roles } from "@/pages/roles.tsx";

const AuthenticatedRoute = ({ children }: { children: ReactNode }) => {
  const { token } = useAuthContext();
  return token ? children : <Navigate to="/login" />;
};

const UnauthenticatedRoute = ({ children }: { children: ReactNode }) => {
  const { token } = useAuthContext();
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      navigate(-1);
    }
  }, [navigate, token]);

  return !token ? children : null;
};

const AppBrowserRouter = createBrowserRouter([
  {
    path: "/",
    children: [
      {
        path: "/",
        element: (
          <UnauthenticatedRoute>
            <Login />
          </UnauthenticatedRoute>
        ),
      },
      {
        path: "/login",
        element: (
          <UnauthenticatedRoute>
            <Login />
          </UnauthenticatedRoute>
        ),
      },
      {
        path: "/reset-password",
        element: (
          <UnauthenticatedRoute>
            <ResetPassword />
          </UnauthenticatedRoute>
        ),
      },
      {
        path: "/",
        element: (
          <AuthenticatedRoute>
            <Main />
          </AuthenticatedRoute>
        ),
        children: [
          {
            path: "/groups",
            element: (
              <AuthenticatedRoute>
                <Groups />
              </AuthenticatedRoute>
            ),
          },
          {
            path: "/users",
            element: (
              <AuthenticatedRoute>
                <Groups />
              </AuthenticatedRoute>
            ),
          },
          {
            path: "/roles",
            element: (
              <AuthenticatedRoute>
                <Roles />
              </AuthenticatedRoute>
            ),
          },
        ],
      },
      {
        path: "*",
        element: <NotFound />,
      },
    ],
  },
]);

function AppRouter() {
  return <RouterProvider router={AppBrowserRouter} />;
}

export default AppRouter;
