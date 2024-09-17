import { createContext, ReactNode, useContext, useState } from "react";

type AlterPasswordDialogContext = {
  isOpen: boolean;
  open: () => void;
  close: () => void;
};

const AlterPasswordDialogContext = createContext<AlterPasswordDialogContext>({
  isOpen: false,
  open: () => {},
  close: () => {},
});

type AlterPasswordDialogProviderProps = {
  children: ReactNode;
};

function AlterPasswordDialogProvider({
  children,
}: AlterPasswordDialogProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => setIsOpen(true);

  const close = () => setIsOpen(false);

  return (
    <AlterPasswordDialogContext.Provider value={{ isOpen, open, close }}>
      {children}
    </AlterPasswordDialogContext.Provider>
  );
}

function useAlterPasswordDialogContext() {
  const context = useContext(AlterPasswordDialogContext);

  if (!context) {
    throw new Error(
      "useAlterPasswordDialogContext must be used within a AlterPasswordDialogProfile",
    );
  }

  return context;
}

export { AlterPasswordDialogProvider, useAlterPasswordDialogContext };
