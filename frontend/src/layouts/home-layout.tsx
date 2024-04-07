import {Outlet} from "react-router-dom";

function HomeLayout() {

  return (
    <div>
      <Outlet/>
    </div>
  )
}

HomeLayout.displayName = "HomeLayout"

export {HomeLayout}