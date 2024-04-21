import {ReactNode, useCallback, useEffect, useState} from "react";
import DarkModeContext from "@/features/dark-mode/contexts/dark-mode-context.tsx";

type DarkModeProviderProps = {
  children: ReactNode
}

function DarkModeProvider({children}: DarkModeProviderProps){

  const isDarkModeCacheSet = () => {
    const darkModeCacheValue = localStorage.getItem("darkMode");
    return darkModeCacheValue === "true" || darkModeCacheValue === "false";
  }

  const getDarkModeInitialState = useCallback(() => {
    if(isDarkModeCacheSet()){ // Caso exista valor no cache, buscar dele
      return localStorage.getItem("darkMode") === "true";
    } else { // Caso não exista valor no cache, buscar das preferências configuradas no navegador
      return window.matchMedia('(prefers-color-scheme: dark)').matches
    }
  }, []);

  const applyDarkMode = (darkMode: boolean) => {
    if (darkMode) {
      document.documentElement.classList.add("dark")
    } else {
      document.documentElement.classList.remove("dark")
    }
  }

  // Aplicar o estado inicial
  useEffect(() => {
    applyDarkMode(getDarkModeInitialState());
  }, [getDarkModeInitialState]);

  const [darkMode, setDarkMode] = useState(getDarkModeInitialState());

  const toggleDarkMode = () => {
    const newDarkMode = !darkMode;

    applyDarkMode(newDarkMode);

    localStorage.setItem("darkMode", String(newDarkMode));
    setDarkMode(newDarkMode);
  }

  return (
    <DarkModeContext.Provider value={{ darkMode, toggleDarkMode }}>
      {children}
    </DarkModeContext.Provider>
  )
}

export {DarkModeProvider}