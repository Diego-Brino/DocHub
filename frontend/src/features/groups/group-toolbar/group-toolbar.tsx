import { Input } from "@/components/custom/input.tsx";
import { FolderMinus, FolderPlus, GitBranchPlus, Search } from "lucide-react";
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
import { useDeleteFolder } from "@/services/folders/use-delete-folder.ts";
import { useMutation } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { toast } from "sonner";

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

const schemaProcess = z.object({
  description: z.string().min(1, "Nome é obrigatório"),
});

function GroupToolbar({
  currentPath,
  setCurrentPath,
  showFolderButtons = true,
}: {
  currentPath: { id: number; name: string }[];
  setCurrentPath: (path: { id: number; name: string }[]) => void;
  showFolderButtons?: boolean;
}) {
  const { token } = useAuthContext();

  const [isAlertOpen, setIsAlertOpen] = useState(false);

  const { filter, setFilter, applyFilter } = useGroupToolbarContext();

  const { id } = useParams();

  const [openNewFolderModal, setOpenNewFolderModal] = useState(false);
  const [openNewFlowModal, setOpenNewFlowModal] = useState(false);

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

  const formProcess = useForm<z.infer<typeof schemaProcess>>({
    resolver: zodResolver(schemaProcess),
    defaultValues: {
      description: "",
    },
  });

  const submitForm = (values: z.infer<typeof schema>) => {
    values.parentFolderId =
      currentPath.length > 0 ? currentPath[currentPath.length - 1].id : null;
    mutateAsync(values).then(() => {
      setOpenNewFolderModal(false);
    });
  };

  const { mutateAsync: mutateAsyncPostServices } = useMutation({
    mutationFn: (values: { description: string }) =>
      axiosClient.post(`/services`, values, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }),
  });

  const submitFormProcess = (values: z.infer<typeof schemaProcess>) => {
    mutateAsyncPostServices(values).then(() => {
      setOpenNewFlowModal(false);
      toast.success("Fluxo criado com sucesso");
    });
  };

  useEffect(() => {
    if (openNewFolderModal) {
      form.reset();
    }
  }, [form, openNewFolderModal]);

  const { mutateAsync: deleteFolder } = useDeleteFolder({
    id:
      currentPath.length > 0
        ? Number(currentPath[currentPath.length - 1].id)
        : null,
  });

  return (
    <>
      <div className="flex justify-between items-center gap-4 bg-muted/60 p-4 rounded-lg border">
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
        <div className="flex gap-4">
          {showFolderButtons && (
            <>
              <Button
                disabled={currentPath.length === 0}
                variant="destructive"
                className="gap-2"
                onClick={() => {
                  setIsAlertOpen(true);
                }}
              >
                <FolderMinus />
                Remover Pasta Atual
              </Button>
              <Button
                className="gap-2"
                onClick={() => {
                  setOpenNewFolderModal(true);
                }}
              >
                <FolderPlus />
                Nova Pasta
              </Button>
            </>
          )}
          {!showFolderButtons && (
            <>
              <Button
                className="gap-2"
                onClick={() => {
                  setOpenNewFlowModal(true);
                }}
              >
                <GitBranchPlus />
                Criar Novo Fluxo
              </Button>
            </>
          )}
        </div>
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
      <AlertDialog
        open={isAlertOpen}
        onOpenChange={(open) => setIsAlertOpen(open)}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja excluir esta pasta?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={() => {
                deleteFolder().finally(() => {
                  setIsAlertOpen(false);
                  setCurrentPath(currentPath?.slice(0, -1));
                });
              }}
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <Dialog
        open={openNewFlowModal}
        onOpenChange={(open) => {
          setOpenNewFlowModal(open);
        }}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Novo Fluxo</DialogTitle>
            <DialogDescription>
              Crie um novo fluxo para gerenciar seus arquivos.
            </DialogDescription>
          </DialogHeader>
          <Form {...formProcess}>
            <form onSubmit={formProcess.handleSubmit(submitFormProcess)}>
              <div className="pb-6 space-y-4">
                <FormField
                  control={formProcess.control}
                  name="description"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome do Fluxo</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={
                            formProcess.formState.errors.description?.message
                          }
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
