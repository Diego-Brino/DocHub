import React from "react";
import {ThemeProvider} from "@/contexts/theme";
import {TooltipProvider} from "@/components/ui/tooltip.tsx";
import {QueryClientProvider} from "react-query";
import queryClient from "@/lib/react-query";
import {Toaster} from "@/components/ui/sonner.tsx";
import {AuthProvider} from "@/contexts/auth";

type AppProviderProps = {
  children: React.ReactNode
}

function AppProvider({children}: AppProviderProps) {
  return (
    <ThemeProvider>
      <TooltipProvider>
        <QueryClientProvider client={queryClient}>
          <AuthProvider>
            {children}
            <Toaster richColors/>
          </AuthProvider>
        </QueryClientProvider>
      </TooltipProvider>
    </ThemeProvider>
  );
}

export default AppProvider;