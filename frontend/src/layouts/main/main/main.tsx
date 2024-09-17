import { Header } from "../header/header.tsx";
import { Content } from "../content/content.tsx";

function Main() {
  return (
    <div className="grid grid-cols-1 grid-rows-[max-content_1fr] min-h-screen">
      <Header />
      <Content />
    </div>
  );
}

Main.displayName = "Main";

export { Main };
