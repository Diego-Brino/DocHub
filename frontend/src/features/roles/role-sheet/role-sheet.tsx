import { createContext, ReactNode, useContext, useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { RoleSheetForm } from "@/features/roles/role-sheet/role-sheet-form.tsx";

type RoleSheetContext = {
  selectedRoleId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
};

const RoleSheetContext = createContext<RoleSheetContext>({
  selectedRoleId: null,
  isOpen: false,
  open: () => {},
  close: () => {},
});

type RoleSheetProviderProps = {
  children: ReactNode;
};

function RoleSheetProvider({ children }: RoleSheetProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedRoleId, setSelectedRoleId] = useState<number | null>(null);

  const open = (id: number | null) => {
    setIsOpen(true);
    setSelectedRoleId(id);
  };

  const close = () => {
    setIsOpen(false);
    setSelectedRoleId(null);
  };

  return (
    <RoleSheetContext.Provider value={{ isOpen, open, close, selectedRoleId }}>
      {children}
    </RoleSheetContext.Provider>
  );
}

function useRoleSheetContext() {
  const context = useContext(RoleSheetContext);

  if (!context) {
    throw new Error("RoleSheetContext must be used within a RoleSheetProvider");
  }

  return context;
}

function RoleSheet() {
  const { isOpen, close, selectedRoleId } = useRoleSheetContext();

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
        <SheetHeader>
          <SheetTitle>Cargo</SheetTitle>
          <SheetDescription>
            {selectedRoleId
              ? "Atualize as informações do cargo"
              : "Crie um novo cargo"}
          </SheetDescription>
        </SheetHeader>
        <RoleSheetForm />
      </SheetContent>
    </Sheet>
  );
}

RoleSheet.displayName = "RoleSheet";

export { RoleSheet, RoleSheetProvider, useRoleSheetContext };
