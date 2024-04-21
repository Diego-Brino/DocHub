import {Sidebar} from "@/layouts/dashboard/components/sidebar.tsx";
import {ReactNode} from "react";
import {Header} from "@/layouts/dashboard/components/header.tsx";

type DashboardProps = {
  children: ReactNode;
}

function Dashboard({children}: DashboardProps) {
  return (
    <div className='w-screen h-screen bg-background'>
      <div
        className='w-full h-full grid grid-cols-[60px_1fr] grid-rows-[60px_1fr] md:grid-cols-[200px_1fr] md:grid-rows-[80px_1fr]'>
        <Header/>
        <Sidebar/>
        <div className='p-4'>
          {children}
        </div>
      </div>
    </div>
  );
}

Dashboard.displayName = "Dashboard"

export {Dashboard};
