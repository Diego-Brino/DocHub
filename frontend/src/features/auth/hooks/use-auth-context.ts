import {useContext} from "react";
import AuthContext from "@/features/auth/contexts/auth-context.tsx";

function useAuthContext(){
  const context = useContext(AuthContext)

  if (!context) {
    throw new Error("useAuthContext must be used within a AuthProvider");
  }

  return context;
}

export {useAuthContext}