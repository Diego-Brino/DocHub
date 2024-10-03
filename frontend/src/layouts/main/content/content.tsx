import { Outlet } from "react-router-dom";

function Content() {
  return (
    <div className="md:container flex justify-center items-start px-4 py-4 md:px-8 md:py-4 overflow-hidden">
      <Outlet />
    </div>
  );
}

Content.displayName = "Content";

export { Content };
