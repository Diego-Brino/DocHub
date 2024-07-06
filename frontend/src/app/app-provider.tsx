import React from "react";
import {DarkModeProvider} from "@/features/dark-mode";
import {TooltipProvider} from "@/components/ui/tooltip.tsx";

type AppProviderProps = {
  children: React.ReactNode
}

function AppProvider({ children }: AppProviderProps) {
  return (
    <DarkModeProvider>
      <TooltipProvider>
        {children}
      </TooltipProvider>
    </DarkModeProvider>
  );
}

export default AppProvider;