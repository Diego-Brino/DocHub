import { createContext, ReactNode, useContext, useState } from "react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { GroupSheetForm } from "@/features/groups/group-sheet/group-sheet-form.tsx";
import { GroupSheetAvatar } from "@/features/groups/group-sheet/group-sheet-avatar.tsx";

type GroupSheetContext = {
  selectedGroupId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
};

const GroupSheetContext = createContext<GroupSheetContext>({
  selectedGroupId: null,
  isOpen: false,
  open: () => {},
  close: () => {},
});

type GroupSheetProviderProps = {
  children: ReactNode;
};

function GroupSheetProvider({ children }: GroupSheetProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedGroupId, setSelectedGroupId] = useState<number | null>(null);

  const open = (id: number | null) => {
    setIsOpen(true);
    setSelectedGroupId(id);
  };

  const close = () => {
    setIsOpen(false);
    setSelectedGroupId(null);
  };

  return (
    <GroupSheetContext.Provider
      value={{ isOpen, open, close, selectedGroupId }}
    >
      {children}
    </GroupSheetContext.Provider>
  );
}

function useGroupSheetContext() {
  const context = useContext(GroupSheetContext);

  if (!context) {
    throw new Error(
      "GroupSheetContext must be used within a GroupSheetProvider",
    );
  }

  return context;
}

function GroupSheet() {
  const { isOpen, close, selectedGroupId } = useGroupSheetContext();

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
        <SheetHeader>
          <SheetTitle>Grupo</SheetTitle>
          <SheetDescription>
            {selectedGroupId
              ? "Atualize as informações do grupo"
              : "Crie um novo grupo"}
          </SheetDescription>
        </SheetHeader>
        <GroupSheetAvatar id={selectedGroupId} />
        <GroupSheetForm />
      </SheetContent>
    </Sheet>
  );
}

GroupSheet.displayName = "GroupSheet";

export { GroupSheet, GroupSheetProvider, useGroupSheetContext };
