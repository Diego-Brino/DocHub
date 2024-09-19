import { HeaderLogo } from "./header-logo.tsx";
import { HeaderNav } from "./header-nav.tsx";
import { UserProfileSheet } from "@/features/users/user-profile-sheet/user-profile-sheet.tsx";
import { UserProfileSheetProvider } from "@/features/users/user-profile-sheet";
import { UserProfileInfo } from "@/features/users";
import { HeaderHamburguer } from "@/layouts/main/header/header-hamburguer.tsx";

function Header() {
  return (
    <UserProfileSheetProvider>
      <div className="bg-muted/60 border-b h-[73px]">
        <div className="md:container h-full hidden justify-between p-4 gap-4 md:flex">
          <HeaderLogo />
          <HeaderNav />
          <UserProfileInfo />
        </div>
        <div className="md:container h-full flex justify-between p-4 gap-4 md:hidden">
          <HeaderLogo />
          <HeaderHamburguer />
        </div>
      </div>
      <UserProfileSheet />
    </UserProfileSheetProvider>
  );
}

Header.displayName = "Header";

export { Header };
