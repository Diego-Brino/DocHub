import { Input } from "@/components/custom/input.tsx";
import { Button } from "@/components/custom/button.tsx";
import { Plus, Search } from "lucide-react";
import { createContext, ReactNode, useContext, useState } from "react";
import { useGroupSheetContext } from "@/features/groups/group-sheet/group-sheet.tsx";

type GroupsToolbarContext = {
  filter: string;
  setFilter: (value: string) => void;
  appliedFilter: string;
  applyFilter: () => void;
};

const GroupsToolbarContext = createContext<GroupsToolbarContext>({
  filter: "",
  setFilter: () => {},
  appliedFilter: "",
  applyFilter: () => {},
});

type GroupsToolbarProviderProps = {
  children: ReactNode;
};

function GroupsToolbarProvider({ children }: GroupsToolbarProviderProps) {
  const [filter, setFilter] = useState<string>("");
  const [appliedFilter, setAppliedFilter] = useState<string>("");

  const applyFilter = () => {
    setAppliedFilter(filter);
  };

  return (
    <GroupsToolbarContext.Provider
      value={{ filter, setFilter, appliedFilter, applyFilter }}
    >
      {children}
    </GroupsToolbarContext.Provider>
  );
}

function useGroupsToolbarContext() {
  const context = useContext(GroupsToolbarContext);

  if (!context) {
    throw new Error(
      "GroupsToolbarContext must be used within a GroupsToolbarProvider",
    );
  }

  return context;
}

function GroupsToolbar() {
  const { filter, setFilter, applyFilter } = useGroupsToolbarContext();
  const { open } = useGroupSheetContext();

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
        Novo Grupo
      </Button>
    </div>
  );
}

export { GroupsToolbar, useGroupsToolbarContext, GroupsToolbarProvider };
