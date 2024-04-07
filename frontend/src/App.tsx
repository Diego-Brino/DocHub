import {Outlet} from "react-router-dom";
import {DarkModeProvider} from "@/providers/dark-mode-provider.tsx";

function App() {
  return (
    <DarkModeProvider>
      <Outlet/>
    </DarkModeProvider>
  )
}

export default App
