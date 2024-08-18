import {ReactNode, useState} from "react";
import AuthContext from "@/features/auth/contexts/auth-context.tsx";

type AuthProviderProps = {
  children: ReactNode
}

function AuthProvider({children}: AuthProviderProps){

  const [token, setToken] = useState<string>('');

  return (
    <AuthContext.Provider value={{ token, setToken }}>
      {children}
    </AuthContext.Provider>
  )
}

export {AuthProvider}