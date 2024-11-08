import { Input } from "@/components/custom/input.tsx";
import { Plus, Search } from "lucide-react";
import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import { Button } from "@/components/custom/button.tsx";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { useParams } from "react-router-dom";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form.tsx";
import { usePostFolder } from "@/services/folders/use-post-folder.ts";

type GroupToolbarContext = {
  filter: string;
  setFilter: (value: string) => void;
  appliedFilter: string;
  applyFilter: () => void;
};

const GroupToolbarContext = createContext<GroupToolbarContext>({
  filter: "",
  setFilter: () => {},
  appliedFilter: "",
  applyFilter: () => {},
});

type GroupToolbarProviderProps = {
  children: ReactNode;
};

function GroupToolbarProvider({ children }: GroupToolbarProviderProps) {
  const [filter, setFilter] = useState<string>("");
  const [appliedFilter, setAppliedFilter] = useState<string>("");

  const applyFilter = () => {
    setAppliedFilter(filter);
  };

  return (
    <GroupToolbarContext.Provider
      value={{ filter, setFilter, appliedFilter, applyFilter }}
    >
      {children}
    </GroupToolbarContext.Provider>
  );
}

function useGroupToolbarContext() {
  const context = useContext(GroupToolbarContext);

  if (!context) {
    throw new Error(
      "GroupToolbarProvider must be used within a GroupToolbarProvider",
    );
  }

  return context;
}

const schema = z.object({
  name: z.string().min(1, "Nome é obrigatório"),
  description: z.string(),
  groupId: z.number(),
  parentFolderId: z.number().nullable(),
});

function GroupToolbar() {
  const { filter, setFilter, applyFilter } = useGroupToolbarContext();

  const { id } = useParams();

  const [openNewFolderModal, setOpenNewFolderModal] = useState(false);

  const { mutateAsync, isLoading } = usePostFolder(Number(id));

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      name: "",
      description: "",
      groupId: Number(id),
      parentFolderId: null,
    },
  });

  const submitForm = (values: z.infer<typeof schema>) => {
    mutateAsync(values).then(() => {
      setOpenNewFolderModal(false);
    });
  };

  useEffect(() => {
    if (openNewFolderModal) {
      form.reset();
    }
  }, [form, openNewFolderModal]);

  return (
    <>
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
        <Button
          className="gap-2"
          onClick={() => {
            setOpenNewFolderModal(true);
          }}
        >
          <Plus />
          Nova Pasta
        </Button>
      </div>
      <Dialog
        open={openNewFolderModal}
        onOpenChange={(open) => {
          setOpenNewFolderModal(open);
        }}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Nova pasta</DialogTitle>
            <DialogDescription>
              Crie uma nova pasta para organizar seus arquivos.
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(submitForm)}>
              <div className="pb-6 space-y-4">
                <FormField
                  control={form.control}
                  name="name"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={form.formState.errors.name?.message}
                        />
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="description"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Descrição</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={form.formState.errors.description?.message}
                        />
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <DialogFooter>
                <Button loading={isLoading} disabled={isLoading}>
                  Confirmar
                </Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </>
  );
}

export { GroupToolbar, useGroupToolbarContext, GroupToolbarProvider };
