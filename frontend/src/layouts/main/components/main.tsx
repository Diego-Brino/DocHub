import {Outlet} from "react-router-dom";
import {Header} from "./header.tsx";

function Main() {
  return (
    <div className='grid grid-cols-1 grid-rows-[max-content_1fr] min-h-screen'>
      <Header/>
      <div className='flex bg-background-60 md:m-8 p-8 rounded md:border backdrop-blur-xl'>
        <Outlet/>
      </div>
    </div>
  )
}

Main.displayName = "Main"

export {Main}
