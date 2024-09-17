import { Bookmark, Group, Users } from "lucide-react";
import { HeaderLinksDesktop } from "./header-links-desktop.tsx";
import { HeaderLinksMobile } from "./header-links-mobile.tsx";
import { useLocation } from "react-router-dom";

const LINKS = [
  { name: "Grupos", to: "/groups", icon: <Group className="size-4" /> },
  { name: "Usuários", to: "/users", icon: <Users className="size-4" /> },
  { name: "Cargos", to: "/roles", icon: <Bookmark className="size-4" /> },
];

function HeaderLinks() {
  const location = useLocation();
  const pathname = location.pathname;

  return (
    <>
      <HeaderLinksMobile links={LINKS} pathname={pathname} />
      <HeaderLinksDesktop links={LINKS} pathname={pathname} />
    </>
  );
}

HeaderLinks.displayName = "HeaderLinks";

export { HeaderLinks };
