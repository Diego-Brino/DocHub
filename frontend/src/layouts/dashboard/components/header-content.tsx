import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Button} from "@/components/custom/button.tsx";
import {
  AppWindow,
  Boxes,
  File,
  KeyRound,
  LogOut,
  Menu,
  ScanEye,
  Settings,
  UserRound,
  UsersRound,
  Waypoints
} from "lucide-react";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger
} from "@/components/ui/dropdown-menu.tsx";
import {Link} from "react-router-dom";
import {DarkModeSwitch} from "@/features/dark-mode/components/dark-mode-switch.tsx";

const MENU_ITEMS = [
  {
    icon: AppWindow,
    title: 'Início',
    link: '#'
  },
  {
    icon: File,
    title: 'Arquivos',
    link: '#'
  },
  {
    icon: Boxes,
    title: 'Grupos',
    link: '#'
  },
  {
    icon: Waypoints,
    title: 'Fluxos',
    link: '#'
  },
  {
    icon: KeyRound,
    title: 'Permissões',
    link: '#'
  },
  {
    icon: ScanEye,
    title: 'Monitoramento',
    link: '#'
  },
  {
    icon: UserRound,
    title: 'Perfil',
    link: '#'
  },
  {
    icon: UsersRound,
    title: 'Usuários',
    link: '#'
  },
  {
    icon: Settings,
    title: 'Configurações',
    link: '#'
  }
]

function HeaderContent() {
  return (
    <div className='flex flex-row-reverse gap-2 p-4 md:p-6 justify-between items-center border-b bg-muted/40'>
      <div className='flex gap-4 items-center justify-center'>
        <div className='hidden sm:flex gap-2 items-center justify-center'>
          <div className='flex gap-2 items-center justify-center bg-accent pl-3 rounded-full'>
            <h1 className='text-base'>Diego Simonaio Brino</h1>
            <Avatar className='size-8'>
              <AvatarImage src='https://avatars.githubusercontent.com/u/68039706?s=48&v=4'/>
              <AvatarFallback>Perfil</AvatarFallback>
            </Avatar>
          </div>
        </div>
        <div className='sm:hidden flex'>
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
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
              <DropdownMenuGroup>
                {MENU_ITEMS.map((item, index) => (
                  <DropdownMenuItem key={index}>
                    <Link to={item.link} className='flex gap-2 items-center w-full'>
                      <item.icon className='size-4'/>
                      <span>{item.title}</span>
                    </Link>
                  </DropdownMenuItem>
                ))}
              </DropdownMenuGroup>
              <DropdownMenuSeparator/>
              <DropdownMenuLabel>
                <DarkModeSwitch/>
              </DropdownMenuLabel>
              <DropdownMenuSeparator/>
              <DropdownMenuItem>
                <Link to='#' className='flex gap-2 items-center'>
                  <LogOut className='size-4'/>
                  <span>Sair</span>
                </Link>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
    </div>
  )
}

HeaderContent.displayName = "HeaderContent"

export {HeaderContent}