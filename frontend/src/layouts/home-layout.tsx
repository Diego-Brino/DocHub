import {Outlet} from "react-router-dom";
import {Header} from "@/layouts/header.tsx";

function HomeLayout() {

  return (
    <div>
      <Header/>
      <Outlet/>
    </div>
  )
}

HomeLayout.displayName = "HomeLayout"

export {HomeLayout}