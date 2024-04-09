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
import {Button} from "@/components/custom/button.tsx";
import {Link} from "react-router-dom";

function Sidebar() {
  return (
    <div className='flex-1 border-r'>
      <nav className='flex flex-col h-full gap-2 p-4 items-start'>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><AppWindow className='size-4'/>Início</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><File className='size-4'/>Arquivos</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><Boxes className='size-4'/>Grupos</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><Waypoints className='size-4'/>Fluxos</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><KeyRound className='size-4'/>Permissões</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><ScanEye className='size-4'/>Monitoramento</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><UserRound className='size-4'/>Perfil</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><UsersRound className='size-4'/>Usuários</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><Settings className='size-4'/>Configurações</Link>
        </Button>
        <Button variant='ghost' asChild className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground'>
          <Link to='#'><LogOut className='size-4'/>Sair</Link>
        </Button>
      </nav>
    </div>
  )
}

Sidebar.displayName = "Sidebar"

export {Sidebar}