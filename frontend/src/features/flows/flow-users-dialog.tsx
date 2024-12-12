import { createContext, ReactNode, useContext, useMemo, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { DataTable } from "@/components/custom/data-table.tsx";
import { ColumnDef } from "@tanstack/react-table";
import { Avatar, AvatarImage } from "@/components/ui/avatar.tsx";
import { Checkbox } from "@/components/ui/checkbox.tsx";
import { useGetUsers } from "@/services/users/use-get-users.ts";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { Flow } from "@/pages/flow.tsx";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";

type FlowUsersDialogContext = {
  selectedFlowId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
};

const FlowUsersDialogContext = createContext<FlowUsersDialogContext>({
  selectedFlowId: null,
  isOpen: false,
  open: () => {},
  close: () => {},
});

type FlowUsersDialogProviderProps = {
  children: ReactNode;
};

function FlowUsersDialogProvider({ children }: FlowUsersDialogProviderProps) {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedFlowId, setSelectedFlowId] = useState<number | null>(null);

  const open = (id: number | null) => {
    setIsOpen(true);
    setSelectedFlowId(id);
  };

  const close = () => {
    setIsOpen(false);
    setSelectedFlowId(null);
  };

  return (
    <FlowUsersDialogContext.Provider
      value={{ isOpen, open, close, selectedFlowId }}
    >
      {children}
    </FlowUsersDialogContext.Provider>
  );
}

function useFlowUsersDialogContext() {
  const context = useContext(FlowUsersDialogContext);

  if (!context) {
    throw new Error(
      "RoleUsersDialogContext must be used within a RoleUsersDialogProvider",
    );
  }

  return context;
}

function FlowUsersDialog() {
  const { token } = useAuthContext();

  const { isOpen, close, selectedFlowId } = useFlowUsersDialogContext();

  const { data: dataGetFlow } = useQuery({
    queryKey: ["flows", selectedFlowId],
    queryFn: async (): Promise<Flow> => {
      const response = await axiosClient.get(`/flows/${selectedFlowId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
    enabled: !!selectedFlowId,
  });

  const { data: dataGetUsers } = useGetUsers();

  const { mutate: mutatePostUserFlow } = useMutation({
    mutationFn: ({ flowId, userId }: { flowId: number; userId: number }) =>
      axiosClient.post(
        `/flow-users`,
        {
          flowId,
          userId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      ),
    onSuccess: () => {
      queryClient.invalidateQueries(["flows"]);
      queryClient.invalidateQueries(["users"]);
      toast.success("Usuário adicionado com sucesso");
    },
  });

  const { mutate: mutateDeleteUserfFlow } = useMutation({
    mutationFn: ({ idFlow, idUser }: { idFlow: number; idUser: number }) =>
      axiosClient.delete(`/flow-users/${idUser}/${idFlow}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }),
    onSuccess: () => {
      queryClient.invalidateQueries(["flows"]);
      queryClient.invalidateQueries(["users"]);
      toast.success("Usuário removido com sucesso");
    },
  });

  const checkedUsers = useMemo(
    () => dataGetFlow?.flowUsers?.map((user) => user.user.id) || [],
    [dataGetFlow, dataGetUsers],
  );

  const columns: ColumnDef<{
    id: number;
    name: string;
    email: string;
    username: string;
    avatarUrl: string;
  }>[] = [
    {
      header: "",
      accessorKey: "id",
      cell: ({ row }) => (
        <div className="flex justify-center">
          <Checkbox
            value={row.getValue("id")}
            checked={checkedUsers.includes(row.getValue("id"))}
            onClick={() => {
              if (checkedUsers.includes(row.getValue("id"))) {
                mutateDeleteUserfFlow({
                  idFlow: selectedFlowId as number,
                  idUser: row.getValue("id"),
                });
              } else {
                mutatePostUserFlow({
                  flowId: selectedFlowId as number,
                  userId: row.getValue("id"),
                });
              }
            }}
          />
        </div>
      ),
      enableColumnFilter: false,
    },
    {
      header: "Avatar",
      accessorKey: "avatarUrl",
      cell: ({ row }) => (
        <Avatar>
          <AvatarImage src={row.getValue("avatarUrl")} />
        </Avatar>
      ),
      enableColumnFilter: false,
    },
    {
      header: "Nome",
      accessorKey: "name",
      cell: ({ row }) => <p className="text-nowrap">{row.getValue("name")}</p>,
    },
    {
      header: "E-mail",
      accessorKey: "email",
    },
  ];

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && close()}>
      <DialogContent className="min-w-[500px] max-w-min">
        <DialogHeader>
          <DialogTitle>Usuários</DialogTitle>
          <DialogDescription>
            Lista de usuários que podem acessar este fluxo.
          </DialogDescription>
        </DialogHeader>
        <DataTable columns={columns} data={dataGetUsers || []} />
      </DialogContent>
    </Dialog>
  );
}

FlowUsersDialog.displayName = "FlowUsersDialog";

export { FlowUsersDialog, FlowUsersDialogProvider, useFlowUsersDialogContext };
