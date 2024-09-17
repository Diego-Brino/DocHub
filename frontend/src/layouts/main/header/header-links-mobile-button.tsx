import { ChevronsUpDown } from "lucide-react";
import { Button, ButtonProps } from "@/components/custom/button.tsx";
import * as React from "react";

export type HeaderLinksMobileButtonLink = {
  name: string;
  to: string;
  icon: React.ReactNode;
};

interface HeaderLinksMobileButtonProps extends ButtonProps {
  link: HeaderLinksMobileButtonLink;
  open: boolean;
}

const HeaderLinksMobileButton = React.forwardRef<
  HTMLButtonElement,
  HeaderLinksMobileButtonProps
>(({ link, open, ...props }: HeaderLinksMobileButtonProps, ref) => {
  return (
    <Button
      {...props}
      ref={ref}
      variant="outline"
      role="combobox"
      aria-expanded={open}
      className="justify-between p-2 w-[125px]"
    >
      <div className="flex gap-1 items-center">
        {link.icon}
        {link.name}
      </div>
      <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
    </Button>
  );
});

HeaderLinksMobileButton.displayName = "HeaderLinksMobileButton";

export { HeaderLinksMobileButton };
