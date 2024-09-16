import {HeaderLogo} from "./header-logo.tsx";
import {HeaderLinks} from "./header-links.tsx";
import {UserProfileSheet} from "@/features/users/components/user-profile-sheet/user-profile-sheet.tsx";
import {UserProfileSheetProvider} from "@/features/users/components/user-profile-sheet";
import {UserProfileInfo} from "@/features/users";

function Header() {
  return (
    <UserProfileSheetProvider>
      <div className='bg-muted/60 border-b h-[73px]'>
        <div className='md:container flex justify-between p-4 gap-4'>
          <HeaderLogo/>
          <HeaderLinks/>
          <UserProfileInfo/>
        </div>
      </div>
      <UserProfileSheet/>
    </UserProfileSheetProvider>
  )
}

Header.displayName = "Header"

export {Header}
