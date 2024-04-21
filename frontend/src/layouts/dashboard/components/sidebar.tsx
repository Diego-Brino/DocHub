import {
  AppWindow,
  Boxes,
  File,
  KeyRound,
  LogOut,
  ScanEye,
  Settings,
  UserRound,
  UsersRound,
  Waypoints
} from "lucide-react";
import {SidebarItem} from "@/layouts/dashboard/components/sidebar-item.tsx";
import {DarkModeSwitch} from "@/features/dark-mode/components/dark-mode-switch.tsx";

const SIDEBAR_ITEMS = [
  {
    icon: <AppWindow/>,
    title: 'Início',
    link: '/dashboard/home'
  },
  {
    icon: <File/>,
    title: 'Arquivos',
    link: '/files'
  },
  {
    icon: <Boxes/>,
    title: 'Grupos',
    link: '/groups'
  },
  {
    icon: <Waypoints/>,
    title: 'Fluxos',
    link: '/flows'
  },
  {
    icon: <KeyRound/>,
    title: 'Permissões',
    link: '/permissions'
  },
  {
    icon: <ScanEye/>,
    title: 'Monitoramento',
    link: '/monitoring'
  },
  {
    icon: <UserRound/>,
    title: 'Perfil',
    link: '/profile'
  },
  {
    icon: <UsersRound/>,
    title: 'Usuários',
    link: '/users'
  },
  {
    icon: <Settings/>,
    title: 'Configurações',
    link: '/settings'
  },
]

function Sidebar() {
  return (
    <nav className='hidden sm:flex items-center justify-between flex-col border-r w-[60px] md:w-auto bg-muted/40'>
      <div className='w-full h-full flex flex-col justify-between items-start'>
        <div className='w-full h-full flex flex-col items-start p-4 gap-4'>
          {SIDEBAR_ITEMS.map((item, index) => (
            <SidebarItem key={index} icon={item.icon} title={item.title} link={item.link}/>
          ))}
        </div>
        <div className='w-full h-full flex flex-col items-center justify-end p-4 gap-4'>
          <DarkModeSwitch/>
        </div>
      </div>
      <div className='w-full border-t gap-4 p-4 flex flex-col-reverse md:flex-row justify-center items-center'>
        <SidebarItem icon={<LogOut/>} title='Sair' link='#'/>
      </div>
    </nav>
  )
}

Sidebar.displayName = "Sidebar"

export {Sidebar}