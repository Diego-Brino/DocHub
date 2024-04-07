import {Moon, Sun} from "lucide-react";
import {useDarkModeContext} from "@/hooks/use-dark-mode-context.ts";

function DarkModeButton() {

  const {
    darkMode,
    toggleDarkMode
  } = useDarkModeContext();

  return (
    <button className='hover:bg-accent p-1 rounded-full' onClick={toggleDarkMode}>
      {darkMode ? <Moon/> : <Sun/>}
    </button>
  )
}

DarkModeButton.displayName = "DarkModeButton"

export {DarkModeButton}