import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
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

type RolePermissionDeleteConfirmationAlertContext = {
  isOpen: boolean;
  open: (onConfirmCallback: () => void) => void;
  confirm: () => void;
  cancel: () => void;
};

const RolePermissionDeleteConfirmationAlertContext =
  createContext<RolePermissionDeleteConfirmationAlertContext>({
    isOpen: false,
    open: () => {},
    confirm: () => {},
    cancel: () => {},
  });

type RolePermissionDeleteConfirmationAlertProviderProps = {
  children: ReactNode;
};

function RolePermissionDeleteConfirmationAlertProvider({
  children,
}: RolePermissionDeleteConfirmationAlertProviderProps) {
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
    <RolePermissionDeleteConfirmationAlertContext.Provider
      value={{ isOpen, open, confirm, cancel }}
    >
      {children}
    </RolePermissionDeleteConfirmationAlertContext.Provider>
  );
}

function useRolePermissionDeleteConfirmationAlertContext() {
  const context = useContext(RolePermissionDeleteConfirmationAlertContext);

  if (!context) {
    throw new Error(
      "useRolePermissionDeleteConfirmationAlertContext must be used within a RolePermissionDeleteConfirmationAlertProvider",
    );
  }

  return context;
}

function RolePermissionDeleteConfirmationAlert() {
  const { isOpen, confirm, cancel } =
    useRolePermissionDeleteConfirmationAlertContext();

  useEffect(() => {
    console.log(isOpen);
  }, [isOpen]);

  return (
    <AlertDialog open={isOpen}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>
            Tem certeza que deseja remover essa permissão?
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

RolePermissionDeleteConfirmationAlert.displayName =
  "RolePermissionDeleteConfirmationAlert";

export {
  RolePermissionDeleteConfirmationAlertProvider,
  useRolePermissionDeleteConfirmationAlertContext,
  RolePermissionDeleteConfirmationAlert,
};
