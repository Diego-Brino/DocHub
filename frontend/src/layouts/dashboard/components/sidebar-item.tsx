import {Button} from "@/components/custom/button.tsx";
import {Link} from "react-router-dom";
import {Tooltip, TooltipContent, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {LucideIcon} from "lucide-react";

type SidebarItemProps = {
  icon: LucideIcon,
  title: string,
  link: string
}

function SidebarItem({icon: Icon, title, link}: SidebarItemProps) {
  return (
    <>
      <div className="w-full flex md:hidden justify-center items-center">
        <Tooltip>
          <TooltipTrigger asChild>
            <Button asChild variant='ghost' className='w-full flex justify-center items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground px-0 h-[27px]'>
              <Link to={link}>
                <Icon className='size-5'/>
                <span className='hidden md:inline'>{title}</span>
              </Link>
            </Button>
          </TooltipTrigger>
          <TooltipContent hideWhenDetached side='right'>
            {title}
          </TooltipContent>
        </Tooltip>
      </div>
      <div className="w-full hidden md:block">
        <Button asChild variant='ghost' className='w-full flex justify-start items-center gap-2 bg-none hover:bg-transparent hover:text-muted-foreground px-0 md:px-4 h-[27px]'>
          <Link to={link}>
            <Icon className='size-5'/>
            <span className='hidden md:inline'>{title}</span>
          </Link>
        </Button>
      </div>
    </>
  )
}

SidebarItem.displayName = "SidebarItem"

export {SidebarItem}