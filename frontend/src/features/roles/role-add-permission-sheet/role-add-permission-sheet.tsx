import { createContext, ReactNode, useContext, useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { RoleAddPermissionSheetForm } from "@/features/roles/role-add-permission-sheet/role-add-permission-sheet-form.tsx";

type RoleAddPermissionSheetContext = {
  isOpen: boolean;
  open: () => void;
  close: () => void;
};

const RoleAddPermissionSheetContext =
  createContext<RoleAddPermissionSheetContext>({
    isOpen: false,
    open: () => {},
    close: () => {},
  });

type RoleAddPermissionSheetProviderProps = {
  children: ReactNode;
};

function RoleAddPermissionSheetProvider({
  children,
}: RoleAddPermissionSheetProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const open = () => {
    setIsOpen(true);
  };

  const close = () => {
    setIsOpen(false);
  };

  return (
    <RoleAddPermissionSheetContext.Provider value={{ isOpen, open, close }}>
      {children}
      <RoleAddPermissionSheet />
    </RoleAddPermissionSheetContext.Provider>
  );
}

function useRoleAddPermissionSheetContext() {
  const context = useContext(RoleAddPermissionSheetContext);

  if (!context) {
    throw new Error(
      "RoleAddPermissionSheetContext must be used within a RoleAddPermissionSheetProvider",
    );
  }

  return context;
}

function RoleAddPermissionSheet() {
  const { isOpen, close } = useRoleAddPermissionSheetContext();

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
        <SheetHeader>
          <SheetTitle>Cargo</SheetTitle>
          <SheetDescription>Adicione permissões ao cargo</SheetDescription>
        </SheetHeader>
        <RoleAddPermissionSheetForm />
      </SheetContent>
    </Sheet>
  );
}

RoleAddPermissionSheet.displayName = "RoleAddPermissionSheet";

export {
  RoleAddPermissionSheet,
  RoleAddPermissionSheetProvider,
  useRoleAddPermissionSheetContext,
};
