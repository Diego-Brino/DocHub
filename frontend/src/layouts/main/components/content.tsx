import {Outlet} from "react-router-dom";

function Content() {
  return (
      <div className='flex bg-background md:m-8 rounded md:border h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px_-_4rem)]'>
        <Outlet/>
      </div>
  )
}

Content.displayName = "Content"

export {Content}
