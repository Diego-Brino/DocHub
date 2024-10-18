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

type UserDeleteConfirmationAlertContext = {
  isOpen: boolean;
  open: (onConfirmCallback: () => void) => void;
  confirm: () => void;
  cancel: () => void;
};

const UserDeleteConfirmationAlertContext =
  createContext<UserDeleteConfirmationAlertContext>({
    isOpen: false,
    open: () => {},
    confirm: () => {},
    cancel: () => {},
  });

type UserDeleteConfirmationAlertProviderProps = {
  children: ReactNode;
};

function UserDeleteConfirmationAlertProvider({
  children,
}: UserDeleteConfirmationAlertProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [onConfirmCallback, setOnConfirmCallback] = useState<() => void>(
    () => {},
  );

  const open = (onConfirmCallback: () => void) => {
    setOnConfirmCallback(() => onConfirmCallback);
    setIsOpen(true);
  };

  const confirm = () => {
    onConfirmCallback();
    setIsOpen(false);
  };

  const cancel = () => setIsOpen(false);

  return (
    <UserDeleteConfirmationAlertContext.Provider
      value={{ isOpen, open, confirm, cancel }}
    >
      {children}
    </UserDeleteConfirmationAlertContext.Provider>
  );
}

function useUserDeleteConfirmationAlertContext() {
  const context = useContext(UserDeleteConfirmationAlertContext);

  if (!context) {
    throw new Error(
      "useUserDeleteConfirmationAlertContext must be used within a UserDeleteConfirmationAlertProvider",
    );
  }

  return context;
}

function UserDeleteConfirmationAlert() {
  const { isOpen, confirm, cancel } = useUserDeleteConfirmationAlertContext();

  return (
    <AlertDialog open={isOpen}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>
            Tem certeza que deseja excluir este usuário?
          </AlertDialogTitle>
          <AlertDialogDescription>
            Esta ação é irreversível e não pode ser desfeita.
          </AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel onClick={cancel}>Cancelar</AlertDialogCancel>
          <AlertDialogAction onClick={confirm}>Confirmar</AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}

UserDeleteConfirmationAlert.displayName = "UserDeleteConfirmationAlert";

export {
  UserDeleteConfirmationAlertProvider,
  useUserDeleteConfirmationAlertContext,
  UserDeleteConfirmationAlert,
};
