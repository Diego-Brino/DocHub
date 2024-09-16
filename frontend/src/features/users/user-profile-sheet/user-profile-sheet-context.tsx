import {createContext, ReactNode, useContext, useState} from "react";

type UserProfileSheetContext = {
  isOpen: boolean,
  open: () => void,
  close: () => void
}

const UserProfileSheetContext = createContext<UserProfileSheetContext>({
  isOpen: false,
  open: () => {},
  close: () => {}
});

type UserProfileSheetProviderProps = {
  children: ReactNode
}

function UserProfileSheetProvider({children}: UserProfileSheetProviderProps){

  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <UserProfileSheetContext.Provider value={{ isOpen, open, close }}>
      {children}
    </UserProfileSheetContext.Provider>
  )
}

function useUserProfileSheetContext(){
  const context = useContext(UserProfileSheetContext);

  if (!context) {
    throw new Error("useUserProfileSheetContext must be used within a UserProfileSheetProvider");
  }

  return context;
}

export {
  UserProfileSheetProvider,
  useUserProfileSheetContext
};