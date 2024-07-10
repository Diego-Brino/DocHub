import React from "react";
import {cn} from "@/lib/utils.ts";
import {CommandItem} from "@/components/custom/command-item.tsx";
import {Link} from "../types/types.ts";
import {useNavigate} from "react-router-dom";

type HeaderLinksMobileCommandItemProps = {
  link: Link,
  pathname: string,
  setOpen: (value: React.SetStateAction<boolean>) => void
}

function HeaderLinksMobileCommandItem({link, pathname, setOpen}: HeaderLinksMobileCommandItemProps) {
  const navigate = useNavigate();

  return (
    <CommandItem
      className={cn(link.to === pathname && 'bg-accent text-accent-foreground')}
      key={link.to}
      value={link.to}
      onSelect={() => {
        setOpen(false);
        navigate(link.to);
      }}
    >
      <div className='flex gap-1 items-center'>
        {link.name}
      </div>
    </CommandItem>
  )
}

HeaderLinksMobileCommandItem.displayName = "HeaderLinksMobileCommandItem"

export {HeaderLinksMobileCommandItem}
