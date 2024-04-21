import {AppWindow, Boxes, File, KeyRound, ScanEye, Settings, UserRound, UsersRound, Waypoints} from "lucide-react";

const LINKS = [
  {
    icon: AppWindow,
    title: 'Início',
    link: '/home'
  },
  {
    icon: File,
    title: 'Arquivos',
    link: '/files'
  },
  {
    icon: Boxes,
    title: 'Grupos',
    link: '/groups'
  },
  {
    icon: Waypoints,
    title: 'Fluxos',
    link: '/flows'
  },
  {
    icon: KeyRound,
    title: 'Permissões',
    link: '/permissions'
  },
  {
    icon: ScanEye,
    title: 'Monitoramento',
    link: '/monitoring'
  },
  {
    icon: UserRound,
    title: 'Perfil',
    link: '/profile'
  },
  {
    icon: UsersRound,
    title: 'Usuários',
    link: '/users'
  },
  {
    icon: Settings,
    title: 'Configurações',
    link: '/settings'
  },
]

export { LINKS };