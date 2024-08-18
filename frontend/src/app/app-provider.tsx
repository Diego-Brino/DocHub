import React from "react";
import {DarkModeProvider} from "@/features/dark-mode";
import {TooltipProvider} from "@/components/ui/tooltip.tsx";
import {QueryClientProvider} from "react-query";
import queryClient from "@/lib/react-query";
import {Toaster} from "@/components/ui/sonner.tsx";
import {AuthProvider} from "@/features/auth/providers/auth-provider.tsx";

type AppProviderProps = {
  children: React.ReactNode
}

function AppProvider({children}: AppProviderProps) {
  return (
    <DarkModeProvider>
      <TooltipProvider>
        <QueryClientProvider client={queryClient}>
          <AuthProvider>
            {children}
            <Toaster/>
          </AuthProvider>
        </QueryClientProvider>
      </TooltipProvider>
    </DarkModeProvider>
  );
}

export default AppProvider;