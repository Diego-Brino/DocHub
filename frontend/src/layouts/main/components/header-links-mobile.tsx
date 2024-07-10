import {Separator} from "@/components/ui/separator.tsx";
import {useCurrentPathname} from "@/layouts/main/hooks/use-current-pathname.ts";
import {HeaderLinksLink} from "@/layouts/main/components/header-links-link.tsx";
import {Fragment, useState} from "react";
import {Popover, PopoverContent, PopoverTrigger} from "@/components/ui/popover.tsx";
import {Button} from "@/components/custom/button.tsx";
import {Bookmark, ChevronsUpDown, Group, Users} from "lucide-react";
import {Command, CommandGroup} from "@/components/ui/command.tsx";
import {cn} from "@/lib/utils.ts";
import {CommandItem} from "@/components/custom/command-item.tsx";

const LINKS = [
  {name: "Grupos", to: "/groups", icon: <Group className='size-4'/>},
  {name: "Usuários", to: "/users", icon: <Users className='size-4'/>},
  {name: "Cargos", to: "/roles", icon: <Bookmark className='size-4'/>}
];

function HeaderLinksMobile() {
  const pathname = useCurrentPathname();

  const [open, setOpen] = useState(false);
  const [currentPathname, setCurrentPathname] = useState(pathname);

  return (
    <>
      <div className='hidden md:flex flex-1 gap-4 items-center justify-center'>
        {LINKS.map((link, index) => (
          <Fragment key={link.to}>
            <HeaderLinksLink to={link.to} selected={pathname === link.to}>
              {link.name}
            </HeaderLinksLink>
            {index < LINKS.length - 1 && <Separator orientation='vertical' className='h-1/2'/>}
          </Fragment>
        ))}
      </div>
      <div className='flex md:hidden gap-4 items-center justify-center'>
        <Popover open={open} onOpenChange={setOpen}>
          <PopoverTrigger asChild>
            <Button
              variant="outline"
              role="combobox"
              aria-expanded={open}
              className="justify-between p-2 w-[125px]"
            >
              <div className='flex gap-1 items-center'>
                {LINKS.find(link => link.to === currentPathname)?.icon}
                {LINKS.find(link => link.to === currentPathname)?.name}
              </div>
              <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
            </Button>
          </PopoverTrigger>
          <PopoverContent className="w-[125px] p-0" hideWhenDetached>
            <Command>
              <CommandGroup>
                {LINKS.map((link) => (
                  <CommandItem
                    className={cn(link.to === currentPathname && 'bg-accent text-accent-foreground')}
                    key={link.to}
                    value={link.to}
                    onSelect={(path) => {
                      setCurrentPathname(path);
                      setOpen(false);
                    }}
                  >
                    <div className='flex gap-1 items-center'>
                      {LINKS.find(link2 => link2.to === link.to)?.name}
                    </div>
                  </CommandItem>
                ))}
              </CommandGroup>
            </Command>
          </PopoverContent>
        </Popover>
      </div>
    </>
  )
}

HeaderLinksMobile.displayName = "HeaderLinksMobile"

export {HeaderLinksMobile}
