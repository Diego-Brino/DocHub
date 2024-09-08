import {ReactNode, useState} from "react";
import UsersProfileSheetAlterPasswordDialogContext
  from "@/features/users/contexts/users-profile-sheet-alter-password-dialog-context.tsx";

type UsersProfileSheetAlterPasswordDialogProviderProps = {
  children: ReactNode
}

function UsersProfileSheetAlterPasswordDialogProvider({children}: UsersProfileSheetAlterPasswordDialogProviderProps){

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <UsersProfileSheetAlterPasswordDialogContext.Provider value={{ isOpen, open, close }}>
      {children}
    </UsersProfileSheetAlterPasswordDialogContext.Provider>
  )
}

export {UsersProfileSheetAlterPasswordDialogProvider};