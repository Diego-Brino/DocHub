import * as React from "react"
import {Eye, EyeOff} from "lucide-react";
import {Input, InputProps} from "@/components/custom/input.tsx";
import {useState} from "react";

export interface PasswordInputProps extends InputProps {
  error?: string
}

const PasswordInput = React.forwardRef<HTMLInputElement, PasswordInputProps>(
  ({ className, error, ...props }, ref) => {

    const [showPassword, setShowPassword] = useState(false);

    const onClickShowPassword = () => {
      setShowPassword(!showPassword)
    }

    return (
      <Input
        {...props}
        className={className}
        ref={ref}
        id={props.id}
        type={showPassword ? 'text' : 'password'}
        error={error}
        endIcon={showPassword ? <Eye className='size-5'/> : <EyeOff className='size-5'/>}
        onClickEndIcon={onClickShowPassword}
      />
    )
  }
)

PasswordInput.displayName = "PasswordInput"

export {PasswordInput}
