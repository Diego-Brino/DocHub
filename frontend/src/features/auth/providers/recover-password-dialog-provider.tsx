import {ReactNode, useState} from "react";
import RecoverPasswordDialogContext from "@/features/auth/contexts/recover-password-dialog-context.tsx";

type RecoverPasswordDialogProviderProps = {
  children: ReactNode
}

function RecoverPasswordDialogProvider({children}: RecoverPasswordDialogProviderProps){

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <RecoverPasswordDialogContext.Provider value={{ isOpen, open, close }}>
      {children}
    </RecoverPasswordDialogContext.Provider>
  )
}

export {RecoverPasswordDialogProvider};