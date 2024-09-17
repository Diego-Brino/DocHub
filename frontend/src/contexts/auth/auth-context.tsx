import { createContext, ReactNode, useContext, useMemo, useState } from "react";

export type TokenPayload = {
  id: number;
  iss: string;
  sub: string;
  iat: number;
  exp: number;
};

type AuthContext = {
  token: string;
  setToken: (value: string) => void;
  tokenPayload?: TokenPayload;
};

const AuthContext = createContext<AuthContext>({
  token: "",
  setToken: () => {},
  tokenPayload: undefined,
});

type AuthProviderProps = {
  children: ReactNode;
};

function extractTokenPayload(token: string): TokenPayload {
  const encodedTokenPayload = token.split(".")[1];
  const decodedTokenPayload = window.atob(encodedTokenPayload);
  return JSON.parse(decodedTokenPayload);
}

function AuthProvider({ children }: AuthProviderProps) {
  const [token, setToken] = useState<string>("");

  const tokenPayload = useMemo(
    () => (token ? extractTokenPayload(token) : undefined),
    [token],
  );

  return (
    <AuthContext.Provider value={{ token, setToken, tokenPayload }}>
      {children}
    </AuthContext.Provider>
  );
}

function useAuthContext() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuthContext must be used within a AuthProvider");
  }

  return context;
}

export { AuthProvider, useAuthContext };
