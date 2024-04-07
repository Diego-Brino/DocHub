import {Header} from "@/layouts/header.tsx";
import {Outlet} from "react-router-dom";
import {withPageTransition} from "@/hocs/with-page-transition.tsx";

function HomeLayout() {
  return (
    <div>
      <Header/>
      <Outlet/>
    </div>
  );
}

const HomeLayoutWithTransition = withPageTransition(HomeLayout);

export { HomeLayoutWithTransition as HomeLayout };