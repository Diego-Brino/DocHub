import * as React from "react";
import { cloneElement } from "react";

import { cn } from "@/lib/utils";

export interface InputProps
  extends React.InputHTMLAttributes<HTMLInputElement> {
  endIcon?: React.ReactElement;
  onClickEndIcon?(event: React.MouseEvent<HTMLDivElement, MouseEvent>): void;
  error?: string;
}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
  ({ className, type, error, endIcon, onClickEndIcon, ...props }, ref) => {
    return (
      <div
        className={cn(
          "relative border border-input rounded-md placeholder:text-muted-foreground focus-within:outline-none focus-within:ring-2 focus-within:ring-ring focus-within:ring-offset-2",
          error && "border-destructive",
          className,
        )}
      >
        <input
          type={type}
          className={cn(
            "flex h-10 w-full rounded-md bg-background px-3 py-2 text-sm file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground disabled:cursor-not-allowed disabled:opacity-50 outline-none",
            className,
            endIcon && "pr-[60px]",
          )}
          ref={ref}
          {...props}
        />
        {endIcon && (
          <div
            className={cn(
              "absolute right-0 -translate-y-full h-full flex justify-center items-center px-3 py-2 bg-primary rounded-r-[5px] text-secondary",
              props.disabled && "cursor-not-allowed opacity-50",
              endIcon && onClickEndIcon && "cursor-pointer hover:bg-primary/90",
            )}
            onClick={(event) => onClickEndIcon && onClickEndIcon(event)}
          >
            {endIcon &&
              cloneElement(endIcon, {
                className: cn("size-5", endIcon.props.className),
              })}
          </div>
        )}
      </div>
    );
  },
);
Input.displayName = "Input";

export { Input };
