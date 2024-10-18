import { createContext, ReactNode, useContext, useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { UserSheetForm } from "@/features/users/user-sheet/user-sheet-form.tsx";

type UserSheetContext = {
  selectedUserId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
};

const UserSheetContext = createContext<UserSheetContext>({
  selectedUserId: null,
  isOpen: false,
  open: () => {},
  close: () => {},
});

type UserSheetProviderProps = {
  children: ReactNode;
};

function UserSheetProvider({ children }: UserSheetProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(null);

  const open = (id: number | null) => {
    setIsOpen(true);
    setSelectedUserId(id);
  };

  const close = () => {
    setIsOpen(false);
    setSelectedUserId(null);
  };

  return (
    <UserSheetContext.Provider value={{ isOpen, open, close, selectedUserId }}>
      {children}
    </UserSheetContext.Provider>
  );
}

function useUserSheetContext() {
  const context = useContext(UserSheetContext);

  if (!context) {
    throw new Error("UserSheetContext must be used within a UserSheetProvider");
  }

  return context;
}

function UserSheet() {
  const { isOpen, close, selectedUserId } = useUserSheetContext();

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
        <SheetHeader>
          <SheetTitle>Usuário</SheetTitle>
          <SheetDescription>
            {selectedUserId
              ? "Atualize as informações do usuário"
              : "Crie um novo usuário"}
          </SheetDescription>
        </SheetHeader>
        <UserSheetForm />
      </SheetContent>
    </Sheet>
  );
}

UserSheet.displayName = "UserSheet";

export { UserSheet, UserSheetProvider, useUserSheetContext };
