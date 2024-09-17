import { createContext, ReactNode, useContext, useState } from "react";

type RecoverPasswordDialogContext = {
  isOpen: boolean;
  open: () => void;
  close: () => void;
};

const RecoverPasswordDialogContext =
  createContext<RecoverPasswordDialogContext>({
    isOpen: false,
    open: () => {},
    close: () => {},
  });

type RecoverPasswordDialogProviderProps = {
  children: ReactNode;
};

function RecoverPasswordDialogProvider({
  children,
}: RecoverPasswordDialogProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <RecoverPasswordDialogContext.Provider value={{ isOpen, open, close }}>
      {children}
    </RecoverPasswordDialogContext.Provider>
  );
}

function useRecoverPasswordDialogContext() {
  const context = useContext(RecoverPasswordDialogContext);

  if (!context) {
    throw new Error(
      "useRecoverPasswordDialogContext must be used within a RecoverPasswordDialogProvider",
    );
  }

  return context;
}

export { RecoverPasswordDialogProvider, useRecoverPasswordDialogContext };
