import {HeaderLogo} from "./header-logo.tsx";
import {HeaderLinks} from "./header-links.tsx";
import {UsersProfileButton} from "@/features/users";

function Header() {
  return (
    <div className='bg-muted/60 border-b h-[73px]'>
      <div className='md:container flex justify-between p-4 gap-4'>
        <HeaderLogo/>
        <HeaderLinks/>
        <UsersProfileButton/>
      </div>
    </div>
  )
}

Header.displayName = "Header"

export {Header}
