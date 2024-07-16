import React from "react";
import {DarkModeProvider} from "@/features/dark-mode";
import {TooltipProvider} from "@/components/ui/tooltip.tsx";
import {QueryClientProvider} from "react-query";
import queryClient from "@/lib/react-query";
import {Toaster} from "@/components/ui/sonner.tsx";

type AppProviderProps = {
  children: React.ReactNode
}

function AppProvider({children}: AppProviderProps) {
  return (
    <DarkModeProvider>
      <TooltipProvider>
        <QueryClientProvider client={queryClient}>
          {children}
        </QueryClientProvider>
      </TooltipProvider>
      <Toaster/>
    </DarkModeProvider>
  );
}

export default AppProvider;