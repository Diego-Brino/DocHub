import { Boxes, Captions, Menu, User, Users } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTrigger,
} from "@/components/ui/sheet.tsx";
import { HeaderLogo } from "@/layouts/main/header/header-logo.tsx";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useUserProfileSheetContext } from "@/features/users/user-profile-sheet";

function HeaderHamburguer() {
  const { open } = useUserProfileSheetContext();
  const [isOpen, setIsOpen] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    const desktopQuery = window.matchMedia("(min-width: 768px)");

    const handleResize = () => {
      if (desktopQuery.matches) {
        setIsOpen(false);
      }
    };

    desktopQuery.addEventListener("change", handleResize);

    return () => {
      desktopQuery.removeEventListener("change", handleResize);
    };
  }, []);

  return (
    <Sheet open={isOpen} onOpenChange={setIsOpen}>
      <SheetTrigger asChild>
        <Button variant="outline" size="icon">
          <Menu className="size-5" />
        </Button>
      </SheetTrigger>
      <SheetContent>
        <SheetHeader>
          <HeaderLogo />
        </SheetHeader>
        <div className="flex flex-col space-y-2 pt-4">
          <Button
            variant="ghost"
            className="justify-start gap-2"
            onClick={() => {
              open();
              setIsOpen(false);
            }}
          >
            <User />
            Perfil
          </Button>
          <Button
            variant="ghost"
            className="justify-start gap-2"
            onClick={() => {
              navigate("/groups");
              setIsOpen(false);
            }}
          >
            <Boxes />
            Grupos
          </Button>
          <Button
            variant="ghost"
            className="justify-start gap-2"
            onClick={() => {
              navigate("/users");
              setIsOpen(false);
            }}
          >
            <Users />
            Usuários
          </Button>
          <Button
            variant="ghost"
            className="justify-start gap-2"
            onClick={() => {
              navigate("/roles");
              setIsOpen(false);
            }}
          >
            <Captions />
            Cargos
          </Button>
        </div>
      </SheetContent>
    </Sheet>
  );
}

HeaderHamburguer.displayName = "HeaderHamburguer";

export { HeaderHamburguer };
