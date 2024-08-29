import {ReactNode, useState} from "react";
import UsersProfileSheetContext from "@/features/users/contexts/users-profile-sheet-context.tsx";

type UsersProfileSheetProviderProps = {
  children: ReactNode
}

function UsersProfileSheetProvider({children}: UsersProfileSheetProviderProps){

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <UsersProfileSheetContext.Provider value={{ isOpen, open, close }}>
      {children}
    </UsersProfileSheetContext.Provider>
  )
}

export {UsersProfileSheetProvider};