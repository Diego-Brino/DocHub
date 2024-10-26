import { Input } from "@/components/custom/input.tsx";
import { Button } from "@/components/custom/button.tsx";
import { Plus, Search } from "lucide-react";
import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import { useUserSheetContext } from "@/features/users/user-sheet/user-sheet.tsx";

type UsersToolbarContext = {
  filter: string;
  setFilter: (value: string) => void;
  appliedFilter: string;
  applyFilter: () => void;
};

const UsersToolbarContext = createContext<UsersToolbarContext>({
  filter: "",
  setFilter: () => {},
  appliedFilter: "",
  applyFilter: () => {},
});

type UsersToolbarProviderProps = {
  children: ReactNode;
};

function UsersToolbarProvider({ children }: UsersToolbarProviderProps) {
  const [filter, setFilter] = useState<string>("");
  const [appliedFilter, setAppliedFilter] = useState<string>("");

  const applyFilter = () => {
    setAppliedFilter(filter);
  };

  useEffect(() => {
    console.log(appliedFilter);
  }, [appliedFilter]);

  return (
    <UsersToolbarContext.Provider
      value={{ filter, setFilter, appliedFilter, applyFilter }}
    >
      {children}
    </UsersToolbarContext.Provider>
  );
}

function useUsersToolbarContext() {
  const context = useContext(UsersToolbarContext);

  if (!context) {
    throw new Error(
      "UsersToolbarContext must be used within a UsersToolbarProvider",
    );
  }

  return context;
}

function UsersToolbar() {
  const { filter, setFilter, applyFilter } = useUsersToolbarContext();
  const { open } = useUserSheetContext();

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
        Novo Usuário
      </Button>
    </div>
  );
}

export { UsersToolbar, useUsersToolbarContext, UsersToolbarProvider };
