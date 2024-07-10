import {HeaderLogo} from "./header-logo.tsx";
import {HeaderLinksMobile} from "./header-links-mobile.tsx";
import {HeaderProfile} from "./header-profile.tsx";

function Header() {
  return (
    <div className='flex justify-between p-4 gap-4 bg-background border-b'>
      <HeaderLogo/>
      <HeaderLinksMobile/>
      <HeaderProfile/>
    </div>
  )
}

Header.displayName = "Header"

export {Header}
