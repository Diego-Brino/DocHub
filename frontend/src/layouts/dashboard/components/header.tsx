import {HeaderLogo} from "@/layouts/dashboard/components/header-logo.tsx";
import {HeaderContent} from "@/layouts/dashboard/components/header-content.tsx";

function Header() {
  return (
    <>
      <HeaderLogo/>
      <HeaderContent/>
    </>
  )
}

Header.displayName = "Header"

export {Header}