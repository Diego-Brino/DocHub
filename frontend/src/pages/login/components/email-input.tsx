import * as React from "react"
import {LucideMail} from "lucide-react";
import {Input, InputProps} from "@/components/custom/input";

export interface EmailInputProps extends InputProps {
  error?: string
}

const EmailInput = React.forwardRef<HTMLInputElement, EmailInputProps>(
  ({ className, error, ...props }, ref) => {
    return (
      <Input
        {...props}
        className={className}
        ref={ref}
        id={props.id}
        type='text'
        error={error}
        endIcon={<LucideMail/>}
      />
    )
  }
)

EmailInput.displayName = "PasswordInput"

export {EmailInput}
