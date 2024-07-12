import {HeaderLogo} from "./header-logo.tsx";
import {HeaderProfile} from "./header-profile.tsx";
import {HeaderLinks} from "./header-links.tsx";

function Header() {
  return (
    <div className='flex justify-between p-4 gap-4 bg-background border-b bg-opacity-60 backdrop-blur-xl h-[73px]'>
      <HeaderLogo/>
      <HeaderLinks/>
      <HeaderProfile/>
    </div>
  )
}

Header.displayName = "Header"

export {Header}
