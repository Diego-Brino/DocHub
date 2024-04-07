import {createContext} from "react";

type DarkModeContext = {
  darkMode: boolean,
  toggleDarkMode: () => void,
}

const darkModeContext = createContext<DarkModeContext>({
  darkMode: false,
  toggleDarkMode: () => {}
});

export default darkModeContext;