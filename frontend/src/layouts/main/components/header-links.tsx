import {useCurrentPathname} from "../hooks/use-current-pathname.ts";
import {useState} from "react";
import {Bookmark, Group, Users} from "lucide-react";
import {HeaderLinksDesktop} from "./header-links-desktop.tsx";
import {HeaderLinksMobile} from "./header-links-mobile.tsx";

const LINKS = [
  {name: "Grupos", to: "/groups", icon: <Group className='size-4'/>},
  {name: "Usuários", to: "/users", icon: <Users className='size-4'/>},
  {name: "Cargos", to: "/roles", icon: <Bookmark className='size-4'/>}
];

function HeaderLinks() {
  const pathname = useCurrentPathname();
  const [currentPathname, setCurrentPathname] = useState(pathname);

  return (
    <>
      <HeaderLinksMobile links={LINKS} />
      <HeaderLinksDesktop links={LINKS}/>
    </>
  )
}

HeaderLinks.displayName = "HeaderLinks"

export {HeaderLinks}
