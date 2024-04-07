import logo from "@/assets/logo.svg"
import logoDark from "@/assets/logo-dark.svg"
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {DarkModeButton} from "@/components/common/dark-mode-button.tsx";
import {useDarkModeContext} from "@/hooks/use-dark-mode-context.ts";
import {Button} from "@/components/custom/button.tsx";
import {Menu} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.tsx";

function Header() {

  const {darkMode} = useDarkModeContext();

  return (
    <div className='border-b bg-background'>
      <div className='container flex gap-2 py-4 justify-between items-center'>
        <div className='flex gap-2 md:gap-4 items-center justify-center'>
          <img src={darkMode ? logoDark : logo} alt="logo" className='size-8 md:size-10'/>
          <h1 className='text-2xl md:text-4xl font-semibold'>DocHub</h1>
        </div>
        <div className='flex gap-4 items-center justify-center'>
          <div className='hidden md:flex gap-2 items-center justify-center'>
            <div className='flex gap-2 items-center justify-center bg-accent pl-3 rounded-full'>
              <h1 className='text-base'>Diego Simonaio Brino</h1>
              <Avatar className='size-8'>
                <AvatarImage src='https://avatars.githubusercontent.com/u/68039706?s=48&v=4'/>
                <AvatarFallback>Perfil</AvatarFallback>
              </Avatar>
            </div>
            <DarkModeButton/>
          </div>
          <div className='md:hidden flex'>
            <DropdownMenu>
              <DropdownMenuTrigger  asChild>
                <Button variant="outline" size="icon">
                  <Menu/>
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent sideOffset={10} align={'end'} alignOffset={0} hideWhenDetached>
                <DropdownMenuLabel>
                  <div className='flex gap-2 items-center justify-center'>
                    <h1 className='text-base'>Diego Simonaio Brino</h1>
                    <Avatar className='size-8'>
                      <AvatarImage src='https://avatars.githubusercontent.com/u/68039706?s=48&v=4'/>
                      <AvatarFallback>Perfil</AvatarFallback>
                    </Avatar>
                  </div>
                </DropdownMenuLabel>
                <DropdownMenuSeparator/>
                <DropdownMenuLabel>
                  <div className='w-full flex gap-2 items-center justify-end'>
                    <DarkModeButton/>
                  </div>
                </DropdownMenuLabel>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        </div>
      </div>
    </div>
  )
}

Header.displayName = "Header"

export {Header}