import { createContext, ReactNode, useContext, useMemo, useState } from "react";
import { useGetRoleUsers } from "@/services/roles/use-get-role-users.ts";
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
import { usePostUserRoles } from "@/services/user-roles/use-post-user-roles.ts";
import { useDeleteUserRoles } from "@/services/user-roles/use-delete-user-roles.ts";

type RoleUsersDialogContext = {
  selectedRoleId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
};

const RoleUsersDialogContext = createContext<RoleUsersDialogContext>({
  selectedRoleId: null,
  isOpen: false,
  open: () => {},
  close: () => {},
});

type RoleUsersDialogProviderProps = {
  children: ReactNode;
};

function RoleUsersDialogProvider({ children }: RoleUsersDialogProviderProps) {
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
    <RoleUsersDialogContext.Provider
      value={{ isOpen, open, close, selectedRoleId }}
    >
      {children}
    </RoleUsersDialogContext.Provider>
  );
}

function useRoleUsersDialogContext() {
  const context = useContext(RoleUsersDialogContext);

  if (!context) {
    throw new Error(
      "RoleUsersDialogContext must be used within a RoleUsersDialogProvider",
    );
  }

  return context;
}

function RoleUsersDialog() {
  const { isOpen, close, selectedRoleId } = useRoleUsersDialogContext();

  const { data: dataGetRoleUsers } = useGetRoleUsers({
    roleId: selectedRoleId,
  });
  const { data: dataGetUsers } = useGetUsers();

  const { mutate: mutatePostUserRoles } = usePostUserRoles();
  const { mutate: mutateDeleteUserRoles } = useDeleteUserRoles();

  const checkedUsers = useMemo(
    () => dataGetRoleUsers?.map((user) => user.id) || [],
    [dataGetRoleUsers, dataGetUsers],
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
                mutateDeleteUserRoles({
                  idRole: selectedRoleId as number,
                  idUser: row.getValue("id"),
                });
              } else {
                mutatePostUserRoles({
                  idRole: selectedRoleId as number,
                  idUser: row.getValue("id"),
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
            Lista de usuários com acesso a este cargo.
          </DialogDescription>
        </DialogHeader>
        <DataTable columns={columns} data={dataGetUsers || []} />
      </DialogContent>
    </Dialog>
  );
}

RoleUsersDialog.displayName = "RoleUsersDialog";

export { RoleUsersDialog, RoleUsersDialogProvider, useRoleUsersDialogContext };
