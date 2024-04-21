import {LogOut} from "lucide-react";
import {SidebarItem} from "@/layouts/dashboard/components/sidebar-item.tsx";
import {DarkModeSwitch} from "@/features/dark-mode";
import {LINKS} from "@/layouts/dashboard/constants/constants.ts";

function Sidebar() {
  return (
    <nav className='hidden sm:flex items-center justify-between flex-col border-r w-[60px] md:w-auto bg-muted/40'>
      <div className='w-full h-full flex flex-col justify-between items-start'>
        <div className='w-full h-full flex flex-col items-start p-4 gap-4'>
          {LINKS.map((item, index) => (
            <SidebarItem key={index} icon={item.icon} title={item.title} link={item.link}/>
          ))}
        </div>
        <div className='w-full h-full flex flex-col items-center justify-end p-4 gap-4'>
          <DarkModeSwitch/>
        </div>
      </div>
      <div className='w-full border-t gap-4 p-4 flex flex-col-reverse md:flex-row justify-center items-center'>
        <SidebarItem icon={LogOut} title='Sair' link='#'/>
      </div>
    </nav>
  )
}

Sidebar.displayName = "Sidebar"

export {Sidebar}