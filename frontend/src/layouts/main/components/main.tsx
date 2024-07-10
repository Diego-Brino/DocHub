import {Outlet} from "react-router-dom";
import {Header} from "./header.tsx";

function Main() {
  return (
    <div className='grid grid-cols-1 grid-rows-2'>
      <Header/>
      <Outlet/>
    </div>
  )
}

Main.displayName = "Main"

export {Main}
