import {useContext} from "react";
import DarkModeContext from "../contexts/dark-mode-context.tsx";

function useDarkModeContext(){
  const context = useContext(DarkModeContext)

  if (!context) {
    throw new Error("useDarkModeContext must be used within a DarkModeProvider");
  }

  return context;
}

export {useDarkModeContext}