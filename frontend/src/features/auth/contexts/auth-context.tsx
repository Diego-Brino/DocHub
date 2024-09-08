import {createContext} from "react";
import {TokenPayload} from "@/features/auth/types";

type AuthContext = {
  token: string
  setToken: (value: string) => void,
  tokenPayload?: TokenPayload,
}

const AuthContext = createContext<AuthContext>({
  token: '',
  setToken: () => {},
  tokenPayload: undefined
});

export default AuthContext;