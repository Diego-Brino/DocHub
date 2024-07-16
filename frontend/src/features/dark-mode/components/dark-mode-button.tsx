import {useDarkModeContext} from "@/features/dark-mode/hooks/use-dark-mode-context.ts";
import {Button} from "@/components/custom/button.tsx";
import {Moon, Sun} from "lucide-react";

function DarkModeButton() {

  const {darkMode, toggleDarkMode} = useDarkModeContext();

  return (
    <Button
      className='rounded-full size-8 p-0'
      variant='outline'
      onClick={toggleDarkMode}>
      {darkMode
        ? <Moon className='size-5'/>
        : <Sun className='size-5'/>
      }
    </Button>
  )
}

DarkModeButton.displayName = 'DarkModeButton'

export {DarkModeButton}