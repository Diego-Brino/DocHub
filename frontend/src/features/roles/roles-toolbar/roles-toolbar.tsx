import { Input } from "@/components/custom/input.tsx";
import { Button } from "@/components/custom/button.tsx";
import { Plus, Search } from "lucide-react";
import { createContext, ReactNode, useContext, useState } from "react";
import { useRoleSheetContext } from "@/features/roles/role-sheet/role-sheet.tsx";

type RolesToolbarContext = {
  filter: string;
  setFilter: (value: string) => void;
  appliedFilter: string;
  applyFilter: () => void;
};

const RolesToolbarContext = createContext<RolesToolbarContext>({
  filter: "",
  setFilter: () => {},
  appliedFilter: "",
  applyFilter: () => {},
});

type RolesToolbarProviderProps = {
  children: ReactNode;
};

function RolesToolbarProvider({ children }: RolesToolbarProviderProps) {
  const [filter, setFilter] = useState<string>("");
  const [appliedFilter, setAppliedFilter] = useState<string>("");

  const applyFilter = () => {
    setAppliedFilter(filter);
  };

  return (
    <RolesToolbarContext.Provider
      value={{ filter, setFilter, appliedFilter, applyFilter }}
    >
      {children}
    </RolesToolbarContext.Provider>
  );
}

function useRolesToolbarContext() {
  const context = useContext(RolesToolbarContext);

  if (!context) {
    throw new Error(
      "RolesToolbarContext must be used within a RolesToolbarProvider",
    );
  }

  return context;
}

function RolesToolbar() {
  const { filter, setFilter, applyFilter } = useRolesToolbarContext();
  const { open } = useRoleSheetContext();

  return (
    <div className="flex justify-between items-center gap-4 mb-4 bg-muted/60 p-4 rounded-lg border">
      <Input
        value={filter}
        placeholder="Filtrar..."
        endIcon={<Search className="size-5" />}
        onChange={(event) => setFilter(event.target.value)}
        onClickEndIcon={() => applyFilter()}
        onKeyDown={(event) => {
          if (event.key === "Enter") {
            applyFilter();
          }
        }}
      />
      <Button className="gap-2" onClick={() => open(null)}>
        <Plus />
        Novo Cargo
      </Button>
    </div>
  );
}

export { RolesToolbar, useRolesToolbarContext, RolesToolbarProvider };
