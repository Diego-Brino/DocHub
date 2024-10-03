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

type RoleDeleteConfirmationAlertContext = {
  isOpen: boolean;
  open: (onConfirmCallback: () => void) => void;
  confirm: () => void;
  cancel: () => void;
};

const RoleDeleteConfirmationAlertContext =
  createContext<RoleDeleteConfirmationAlertContext>({
    isOpen: false,
    open: () => {},
    confirm: () => {},
    cancel: () => {},
  });

type RoleDeleteConfirmationAlertProviderProps = {
  children: ReactNode;
};

function RoleDeleteConfirmationAlertProvider({
  children,
}: RoleDeleteConfirmationAlertProviderProps) {
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
    <RoleDeleteConfirmationAlertContext.Provider
      value={{ isOpen, open, confirm, cancel }}
    >
      {children}
    </RoleDeleteConfirmationAlertContext.Provider>
  );
}

function useRoleDeleteConfirmationAlertContext() {
  const context = useContext(RoleDeleteConfirmationAlertContext);

  if (!context) {
    throw new Error(
      "useRoleDeleteConfirmationAlertContext must be used within a RoleDeleteConfirmationAlertProvider",
    );
  }

  return context;
}

function RoleDeleteConfirmationAlert() {
  const { isOpen, confirm, cancel } = useRoleDeleteConfirmationAlertContext();

  return (
    <AlertDialog open={isOpen}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>
            Tem certeza que deseja deletar este cargo?
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

RoleDeleteConfirmationAlert.displayName = "RoleDeleteConfirmationAlert";

export {
  RoleDeleteConfirmationAlertProvider,
  useRoleDeleteConfirmationAlertContext,
  RoleDeleteConfirmationAlert,
};
