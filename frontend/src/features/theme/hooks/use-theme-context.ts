import {useContext} from "react";
import {ThemeProviderContext} from "../contexts/theme-context.tsx";

function useThemeContext(){
  const context = useContext(ThemeProviderContext)

  if (!context) {
    throw new Error("useThemeContext must be used within a ThemeProvider");
  }

  return context;
}

export {useThemeContext}