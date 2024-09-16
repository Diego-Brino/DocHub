import * as SwitchPrimitives from "@radix-ui/react-switch";
import React from "react";
import {cn} from "@/lib/utils.ts";
import {Moon, Sun} from "lucide-react";
import {useThemeContext} from "@/contexts/theme";

const ThemeSwitch = React.forwardRef<
  React.ElementRef<typeof SwitchPrimitives.Root>,
  React.ComponentPropsWithoutRef<typeof SwitchPrimitives.Root>
>(({ className, ...props }, ref) => {

  const {theme, setTheme} = useThemeContext();

  return(
    <SwitchPrimitives.Root
      checked={theme === 'dark'}
      onCheckedChange={() => setTheme(theme === 'light' ? 'dark' : 'light')}
      className={cn(
        "relative peer inline-flex h-6 w-11 shrink-0 cursor-pointer items-center rounded-full border-2 border-transparent transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 focus-visible:ring-offset-background disabled:cursor-not-allowed disabled:opacity-50 data-[state=checked]:bg-primary data-[state=unchecked]:bg-input",
        className
      )}
      {...props}
      ref={ref}
    >
      <Moon className={cn('size-4 absolute left-0.5 text-secondary', theme === 'dark' ? 'block' : 'hidden')}/>
      <SwitchPrimitives.Thumb
        className={cn(
          "pointer-events-none block h-5 w-5 rounded-full bg-background shadow-lg ring-0 transition-transform data-[state=checked]:translate-x-5 data-[state=unchecked]:translate-x-0"
        )}
      />
      <Sun className={cn('size-4 absolute right-0.5',  theme === 'dark' ? 'hidden' : 'block')}/>
    </SwitchPrimitives.Root>
  )
})

ThemeSwitch.displayName = 'ThemeSwitch'

export { ThemeSwitch }