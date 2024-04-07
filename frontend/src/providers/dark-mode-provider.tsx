import {ReactNode, useEffect, useState} from "react";
import DarkModeContext from "@/contexts/dark-mode-context.tsx";

type DarkModeProviderProps = {
  children: ReactNode
}

function DarkModeProvider({children}: DarkModeProviderProps){

  const getDarkModeCacheState = () => {
    return localStorage.getItem("darkMode") === "true";
  }

  const [darkMode, setDarkMode] = useState(getDarkModeCacheState());

  const toggleDarkMode = () => {
    setDarkMode(!darkMode);
  }

  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add("dark")
    } else {
      document.documentElement.classList.remove("dark")
    }
    localStorage.setItem("darkMode", String(darkMode));
  }, [darkMode]);

  return (
    <DarkModeContext.Provider value={{darkMode, toggleDarkMode}}>
      {children}
    </DarkModeContext.Provider>
  )
}

export {DarkModeProvider}