import * as React from "react"

import { cn } from "@/lib/utils"

export interface InputValidationProps {
  className?: string
  error?: string
}

const InputValidation = React.forwardRef<HTMLInputElement, InputValidationProps>(
  ({ error, className }, ref) => {
    return (
      <p ref={ref} className={cn('text-xs text-red-500', className)}>{error && error}</p>
    )
  }
)

InputValidation.displayName = "InputValidation"

export { InputValidation }
