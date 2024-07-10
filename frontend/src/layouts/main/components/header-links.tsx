import {useCurrentPathname} from "../hooks/use-current-pathname.ts";
import {Bookmark, Group, Users} from "lucide-react";
import {HeaderLinksDesktop} from "./header-links-desktop.tsx";
import {HeaderLinksMobile} from "./header-links-mobile.tsx";
import {Link} from "@/layouts/main/types/types.ts";

const LINKS: Link[] = [
  {name: "Grupos", to: "/groups", icon: <Group className='size-4'/>},
  {name: "Usuários", to: "/users", icon: <Users className='size-4'/>},
  {name: "Cargos", to: "/roles", icon: <Bookmark className='size-4'/>}
];

function HeaderLinks() {
  const pathname = useCurrentPathname();

  return (
    <>
      <HeaderLinksMobile links={LINKS} pathname={pathname}/>
      <HeaderLinksDesktop links={LINKS} pathname={pathname}/>
    </>
  )
}

HeaderLinks.displayName = "HeaderLinks"

export {HeaderLinks}
