import {Outlet} from "react-router-dom";

function Content() {
  return (
    <div className='md:container flex justify-center items-start md:py-8 overflow-hidden'>
      <Outlet/>
    </div>
  )
}

Content.displayName = "Content"

export {Content}
