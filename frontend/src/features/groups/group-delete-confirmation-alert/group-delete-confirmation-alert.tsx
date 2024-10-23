import { createContext, ReactNode, useContext, useState } from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog.tsx";

type GroupDeleteConfirmationAlertContext = {
  isOpen: boolean;
  open: (onConfirmCallback: () => void) => void;
  confirm: () => void;
  cancel: () => void;
};

const GroupDeleteConfirmationAlertContext =
  createContext<GroupDeleteConfirmationAlertContext>({
    isOpen: false,
    open: () => {},
    confirm: () => {},
    cancel: () => {},
  });

type GroupDeleteConfirmationAlertProviderProps = {
  children: ReactNode;
};

function GroupDeleteConfirmationAlertProvider({
  children,
}: GroupDeleteConfirmationAlertProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [onConfirmCallback, setOnConfirmCallback] = useState<() => void>(
    () => () => {},
  );

  const open = (callback: () => void) => {
    setIsOpen(true);
    setOnConfirmCallback(() => callback);
  };

  const confirm = () => {
    onConfirmCallback();
    setIsOpen(false);
  };

  const cancel = () => {
    setIsOpen(false);
  };

  return (
    <GroupDeleteConfirmationAlertContext.Provider
      value={{ isOpen, open, confirm, cancel }}
    >
      {children}
      <AlertDialog open={isOpen} onOpenChange={cancel}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja excluir este grupo?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={confirm}>Confirmar</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </GroupDeleteConfirmationAlertContext.Provider>
  );
}

function useGroupDeleteConfirmationAlert() {
  const context = useContext(GroupDeleteConfirmationAlertContext);

  if (!context) {
    throw new Error(
      "GroupDeleteConfirmationAlertContext must be used within a GroupDeleteConfirmationAlertProvider",
    );
  }

  return context;
}

export {
  GroupDeleteConfirmationAlertProvider,
  useGroupDeleteConfirmationAlert,
};
