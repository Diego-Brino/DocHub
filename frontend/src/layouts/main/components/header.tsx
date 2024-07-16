import {HeaderLogo} from "./header-logo.tsx";
import {HeaderProfile} from "./header-profile.tsx";
import {HeaderLinks} from "./header-links.tsx";

function Header() {
  return (
    <div className='bg-muted/60 border-b h-[73px]'>
      <div className='container flex justify-between py-4 gap-4'>
        <HeaderLogo/>
        <HeaderLinks/>
        <HeaderProfile/>
      </div>
    </div>
  )
}

Header.displayName = "Header"

export {Header}
