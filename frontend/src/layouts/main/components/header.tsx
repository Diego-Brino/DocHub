import {HeaderLogo} from "./header-logo.tsx";
import {HeaderLinks} from "./header-links.tsx";
import {UsersProfile} from "@/features/users";
import {UsersProfileSheet} from "@/features/users/components/users-profile-sheet.tsx";
import {UsersProfileSheetProvider} from "@/features/users/providers/users-profile-sheet-provider.tsx";

function Header() {
  return (
    <UsersProfileSheetProvider>
      <div className='bg-muted/60 border-b h-[73px]'>
        <div className='md:container flex justify-between p-4 gap-4'>
          <HeaderLogo/>
          <HeaderLinks/>
          <UsersProfile/>
        </div>
      </div>
      <UsersProfileSheet/>
    </UsersProfileSheetProvider>
  )
}

Header.displayName = "Header"

export {Header}
