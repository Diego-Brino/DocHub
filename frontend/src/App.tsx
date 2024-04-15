import {Outlet} from "react-router-dom";
import {DarkModeProvider} from "@/providers/dark-mode-provider.tsx";
import {TooltipProvider} from "@/components/ui/tooltip.tsx";

function App() {
  return (
    <DarkModeProvider>
      <TooltipProvider>
        <Outlet/>
      </TooltipProvider>
    </DarkModeProvider>
  )
}

export default App
