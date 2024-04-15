import {RightHeader} from "@/layouts/right-header.tsx";
import {Sidebar} from "@/layouts/sidebar.tsx";
import {ReactNode} from "react";
import {LeftHeader} from "@/layouts/left-header.tsx";

type DashboardProps = {
  children?: ReactNode
}

function Dashboard({children}: DashboardProps) {
  return (
    <div className='w-full h-full bg-background'>
      <div className='w-full h-full grid grid-cols-[60px_1fr] grid-rows-[60px_1fr] md:grid-cols-[200px_1fr] md:grid-rows-[80px_1fr]'>
        <LeftHeader/>
        <RightHeader/>
        <Sidebar/>
        <div className='p-4'>
          {children}
        </div>
      </div>
    </div>
  )
}

Dashboard.displayName = "Dashboard";

export {Dashboard};
