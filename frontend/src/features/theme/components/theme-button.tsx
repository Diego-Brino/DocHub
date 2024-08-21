import {useThemeContext} from "@/features/theme/hooks/use-theme-context.ts";
import {Button} from "@/components/custom/button.tsx";
import {Moon, Sun} from "lucide-react";

function ThemeButton() {

  const {theme, setTheme} = useThemeContext();

  return (
    <Button
      className='rounded-full size-8 p-0'
      variant='outline'
      onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>
      {theme === 'light'
        ? <Sun className='size-5'/>
        : <Moon className='size-5'/>
      }
    </Button>
  )
}

ThemeButton.displayName = 'ThemeButton'

export {ThemeButton}