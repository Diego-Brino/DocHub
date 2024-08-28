import {ReactNode, useMemo, useState} from "react";
import AuthContext from "@/features/auth/contexts/auth-context.tsx";
import {extractTokenPayload} from "@/features/auth";

type AuthProviderProps = {
  children: ReactNode
}

function AuthProvider({children}: AuthProviderProps){

  const [token, setToken] = useState<string>('');

  const tokenPayload = useMemo(() => token ? extractTokenPayload(token) : undefined, [token]);

  return (
    <AuthContext.Provider value={{ token, setToken, tokenPayload }}>
      {children}
    </AuthContext.Provider>
  )
}

export {AuthProvider}