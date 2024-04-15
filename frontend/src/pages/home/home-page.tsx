import {withPageTransition} from "@/hocs/with-page-transition.tsx";
import {Dashboard} from "@/layouts/dashboard.tsx";

function HomePage() {
  return (
    <Dashboard>
      TESTE
    </Dashboard>
  )
}

const HomePageWithTransition = withPageTransition(HomePage);

export {HomePageWithTransition as HomePage};
