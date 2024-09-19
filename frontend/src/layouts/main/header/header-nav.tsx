import { Bookmark, Group, Users } from "lucide-react";
import { useLocation } from "react-router-dom";
import { Fragment } from "react";
import { Separator } from "@/components/ui/separator.tsx";
import { HeaderNavLink } from "@/layouts/main/header/header-nav-link.tsx";

const LINKS = [
  { name: "Grupos", to: "/groups", icon: <Group className="size-4" /> },
  { name: "Usuários", to: "/users", icon: <Users className="size-4" /> },
  { name: "Cargos", to: "/roles", icon: <Bookmark className="size-4" /> },
];

function HeaderNav() {
  const location = useLocation();
  const pathname = location.pathname;

  return (
    <div className="hidden md:flex flex-1 gap-4 items-center justify-center">
      {LINKS.map((link, index) => (
        <Fragment key={link.to}>
          <HeaderNavLink to={link.to} selected={pathname === link.to}>
            {link.name}
          </HeaderNavLink>
          {index < LINKS.length - 1 && (
            <Separator orientation="vertical" className="h-1/2" />
          )}
        </Fragment>
      ))}
    </div>
  );
}

HeaderNav.displayName = "HeaderNav";

export { HeaderNav };
