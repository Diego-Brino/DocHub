import {Separator} from "@/components/ui/separator.tsx";
import {Fragment} from "react";
import {Link} from "../types/types.ts";
import {HeaderLinksDesktopLink} from "./header-links-desktop-link.tsx";

type HeaderLinksDesktopProps = {
  links: Link[],
  pathname: string
}

function HeaderLinksDesktop({links, pathname}: HeaderLinksDesktopProps) {
  return (
    <div className='hidden md:flex flex-1 gap-4 items-center justify-center'>
      {links.map((link, index) => (
        <Fragment key={link.to}>
          <HeaderLinksDesktopLink to={link.to} selected={pathname === link.to}>
            {link.name}
          </HeaderLinksDesktopLink>
          {index < links.length - 1 && <Separator orientation='vertical' className='h-1/2'/>}
        </Fragment>
      ))}
    </div>
  )
}

HeaderLinksDesktop.displayName = "HeaderLinksDesktop"

export {HeaderLinksDesktop}
