import { useState } from "react";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover.tsx";
import { Command, CommandGroup } from "@/components/ui/command.tsx";
import { HeaderLinksMobileCommandItem } from "./header-links-mobile-command-item.tsx";
import {
  HeaderLinksMobileButton,
  HeaderLinksMobileButtonLink,
} from "./header-links-mobile-button.tsx";

type HeaderLinksMobileProps = {
  links: HeaderLinksMobileButtonLink[];
  pathname: string;
};

function HeaderLinksMobile({ links, pathname }: HeaderLinksMobileProps) {
  const [open, setOpen] = useState(false);

  const currentLink = (() => {
    return links.find((link) => link.to === pathname);
  })();

  return (
    <div className="flex md:hidden gap-4 items-center justify-center">
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          {currentLink && (
            <HeaderLinksMobileButton link={currentLink} open={open} />
          )}
        </PopoverTrigger>
        <PopoverContent className="w-[125px] p-0" hideWhenDetached>
          <Command>
            <CommandGroup>
              {links.map((link) => (
                <HeaderLinksMobileCommandItem
                  key={link.to}
                  link={link}
                  pathname={pathname}
                  setOpen={setOpen}
                />
              ))}
            </CommandGroup>
          </Command>
        </PopoverContent>
      </Popover>
    </div>
  );
}

HeaderLinksMobile.displayName = "HeaderLinksMobile";

export { HeaderLinksMobile };
