import {Header} from "@/layouts/header.tsx";
import {Sidebar} from "@/layouts/sidebar.tsx";
import {withPageTransition} from "@/hocs/with-page-transition.tsx";

function HomePage() {
  return (
    <div className='min-h-screen flex flex-col'>
      <Header/>
      <div className='flex flex-col flex-1 gap-4 bg-background border-x'>
        <div className='container flex flex-1 gap-4 bg-background'>
          <Sidebar/>
          <div className='flex-[5] h-full'>

          </div>
        </div>
      </div>
    </div>
  )
}

const HomePageWithTransition = withPageTransition(HomePage);

export { HomePageWithTransition as HomePage };
