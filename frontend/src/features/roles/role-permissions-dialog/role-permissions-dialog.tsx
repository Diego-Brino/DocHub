import { createContext, ReactNode, useContext, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { DataTable } from "@/components/custom/data-table.tsx";
import { ColumnDef } from "@tanstack/react-table";
import { useGetRole } from "@/services/roles/use-get-role.ts";
import {
  Tabs,
  TabsContent,
  TabsList,
  TabsTrigger,
} from "@/components/ui/tabs.tsx";
import { useDeleteSystemRolePermissions } from "@/services/system-role-permissions/use-delete-system-role-permissions.ts";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
import { Button } from "@/components/custom/button.tsx";
import { TrashIcon } from "lucide-react";
import { useRolePermissionDeleteConfirmationAlertContext } from "@/features/roles/role-permission-delete-confirmation-alert/role-permission-delete-confirmation-alert.tsx";
import { useRoleAddPermissionSheetContext } from "@/features/roles/role-add-permission-sheet/role-add-permission-sheet.tsx";
import { useDeleteGroupRolePermissions } from "@/services/group-role-permissions/use-delete-group-role-permissions.ts";
import {
  Archive,
  Folder,
} from "@/services/groups/use-get-group-root-resources.ts";
import { useDeleteResourceRolePermissions } from "@/services/resource-role-permissions/use-delete-resource-role-permissions.ts";

type RolePermissionsDialogContext = {
  selectedRoleId: number | null;
  isOpen: boolean;
  open: (id: number | null) => void;
  close: () => void;
  resourceFlag: boolean;
  selectedResource: Archive | Folder | null;
  setSelectedResource: (resource: Archive | Folder | null) => void;
};

const RolePermissionsDialogContext =
  createContext<RolePermissionsDialogContext>({
    selectedRoleId: null,
    isOpen: false,
    open: () => {},
    close: () => {},
    resourceFlag: false,
    selectedResource: null,
    setSelectedResource: () => {},
  });

type RolePermissionsDialogProviderProps = {
  children: ReactNode;
  resourceFlag?: boolean;
};

function RolePermissionsDialogProvider({
  children,
  resourceFlag = false,
}: RolePermissionsDialogProviderProps) {
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
    <RolePermissionsDialogContext.Provider
      value={{
        isOpen,
        open,
        close,
        selectedRoleId,
        resourceFlag,
        selectedResource: null,
        setSelectedResource: () => {},
      }}
    >
      {children}
    </RolePermissionsDialogContext.Provider>
  );
}

function useRolePermissionsDialogContext() {
  const context = useContext(RolePermissionsDialogContext);

  if (!context) {
    throw new Error(
      "RolePermissionsDialogContext must be used within a RolePermissionsDialogProvider",
    );
  }

  return context;
}

function RolePermissionsDialog() {
  const { isOpen, close, selectedRoleId, resourceFlag } =
    useRolePermissionsDialogContext();

  const { open } = useRolePermissionDeleteConfirmationAlertContext();
  const { open: openRoleAddPermissionSheet } =
    useRoleAddPermissionSheetContext();

  const { data } = useGetRole({ id: selectedRoleId });

  const { mutate: mutateDeleteSystemRolePermissions } =
    useDeleteSystemRolePermissions();
  const { mutate: mutateDeleteGroupRolePermissions } =
    useDeleteGroupRolePermissions();
  const { mutate: mutateDeleteResourceRolePermissions } =
    useDeleteResourceRolePermissions();

  const handleDelete = (idSystemPermission: number) => {
    mutateDeleteSystemRolePermissions({
      idRole: selectedRoleId as number,
      idSystemPermission: idSystemPermission,
    });
  };

  const handleDeleteGroupPermission = (
    idGroupPermission: number,
    idGroup: number,
  ) => {
    mutateDeleteGroupRolePermissions({
      idRole: selectedRoleId as number,
      idGroupPermission,
      idGroup,
    });
  };

  const handleDeleteResourcePermission = (
    idResourcePermission: number,
    idResource: number,
  ) => {
    mutateDeleteResourceRolePermissions({
      idRole: selectedRoleId as number,
      idResourcePermission,
      idResource,
    });
  };

  const columnsSystem: ColumnDef<{ id: number; description: string }>[] = [
    {
      header: "Descrição",
      accessorKey: "description",
      cell: ({ row }) => (
        <p className="text-nowrap">{row.getValue("description")}</p>
      ),
    },
    {
      header: "Ações",
      accessorKey: "id",
      enableColumnFilter: false,
      cell: ({ row }) => (
        <Tooltip>
          <TooltipTrigger asChild>
            <Button
              variant="outline"
              size="icon"
              onClick={() => open(() => handleDelete(row.getValue("id")))}
            >
              <TrashIcon className="size-5" />
            </Button>
          </TooltipTrigger>
          <TooltipContent>Excluir</TooltipContent>
        </Tooltip>
      ),
    },
  ];

  const columnsGroup: ColumnDef<{
    group: { id: number; name: string; description: string; groupUrl: string };
    permission: { id: number; description: string };
  }>[] = [
    {
      header: "Grupo",
      accessorKey: "group",
      accessorFn: (row) => row.group.name,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.group.name}</p>
      ),
    },
    {
      header: "Permissões",
      accessorKey: "permission",
      accessorFn: (row) => row.permission.description,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.permission.description}</p>
      ),
    },
    {
      header: "Ações",
      accessorKey: "id",
      enableColumnFilter: false,
      cell: ({ row }) => (
        <Tooltip>
          <TooltipTrigger asChild>
            <Button
              variant="outline"
              size="icon"
              onClick={() =>
                open(() =>
                  handleDeleteGroupPermission(
                    row.original.permission.id,
                    row.original.group.id,
                  ),
                )
              }
            >
              <TrashIcon className="size-5" />
            </Button>
          </TooltipTrigger>
          <TooltipContent>Excluir</TooltipContent>
        </Tooltip>
      ),
    },
  ];

  const columnsResource: ColumnDef<{
    resource: Archive | Folder;
    permission: { id: number; description: string };
  }>[] = [
    {
      header: "Recurso",
      accessorKey: "name",
      accessorFn: (row) => row.resource.name,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.resource.name}</p>
      ),
    },
    {
      header: "Descrição",
      accessorKey: "description",
      accessorFn: (row) => row.resource.description,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.resource.description}</p>
      ),
    },
    {
      header: "Permissão",
      accessorKey: "permission",
      accessorFn: (row) => row.permission.description,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.permission.description}</p>
      ),
    },
    {
      header: "Ações",
      accessorKey: "id",
      enableColumnFilter: false,
      cell: ({ row }) => (
        <Tooltip>
          <TooltipTrigger asChild>
            <Button
              variant="outline"
              size="icon"
              onClick={() =>
                open(() =>
                  handleDeleteResourcePermission(
                    row.original.permission.id,
                    row.original.resource.id,
                  ),
                )
              }
            >
              <TrashIcon className="size-5" />
            </Button>
          </TooltipTrigger>
          <TooltipContent>Excluir</TooltipContent>
        </Tooltip>
      ),
    },
  ];

  const columnsResource2: ColumnDef<{
    role: { id: number; name: string; description: string };
    resource: Archive | Folder;
    permission: { id: number; description: string };
  }>[] = [
    {
      header: "Cargo",
      accessorKey: "description",
      accessorFn: (row) => row.resource.description,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.resource.description}</p>
      ),
    },
    {
      header: "Permissão",
      accessorKey: "permission",
      accessorFn: (row) => row.permission.description,
      cell: ({ row }) => (
        <p className="text-nowrap">{row.original.permission.description}</p>
      ),
    },
    {
      header: "Ações",
      accessorKey: "id",
      enableColumnFilter: false,
      cell: ({ row }) => (
        <Tooltip>
          <TooltipTrigger asChild>
            <Button
              variant="outline"
              size="icon"
              onClick={() =>
                open(() =>
                  handleDeleteResourcePermission(
                    row.original.permission.id,
                    row.original.resource.id,
                  ),
                )
              }
            >
              <TrashIcon className="size-5" />
            </Button>
          </TooltipTrigger>
          <TooltipContent>Excluir</TooltipContent>
        </Tooltip>
      ),
    },
  ];

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && close()}>
      <DialogContent className="min-w-[500px] max-w-min">
        <DialogHeader>
          <DialogTitle>Permissões</DialogTitle>
          <DialogDescription>
            Lista de permissões do {resourceFlag ? "Recurso" : "Cargo"}.
          </DialogDescription>
        </DialogHeader>
        {!resourceFlag ? (
          <Tabs defaultValue="system" className="w-full">
            <TabsList className="w-full">
              <TabsTrigger className="w-full" value="system">
                Sistema
              </TabsTrigger>
              <TabsTrigger className="w-full" value="group">
                Grupo
              </TabsTrigger>
              <TabsTrigger className="w-full" value="resource">
                Recursos
              </TabsTrigger>
            </TabsList>
            <TabsContent value="system">
              <div className="flex flex-col gap-4 pt-2">
                <Button onClick={() => openRoleAddPermissionSheet("system")}>
                  Adicionar permissão de sistema
                </Button>
                <DataTable
                  columns={columnsSystem}
                  data={data?.systemPermissions || []}
                />
              </div>
            </TabsContent>
            <TabsContent value="group">
              <div className="flex flex-col gap-4 pt-2">
                <Button onClick={() => openRoleAddPermissionSheet("group")}>
                  Adicionar permissão de grupo
                </Button>
                <DataTable
                  columns={columnsGroup}
                  data={(() => {
                    const flattenedArray = data?.groupPermissions.flatMap(
                      (group) =>
                        group.permissions.map((permission) => ({
                          group: group.group,
                          permission,
                        })),
                    );
                    return flattenedArray || [];
                  })()}
                />
              </div>
            </TabsContent>
            <TabsContent value="resource">
              <div className="flex flex-col gap-4 pt-2">
                <Button onClick={() => openRoleAddPermissionSheet("resource")}>
                  Adicionar permissão de recurso
                </Button>
                <DataTable
                  columns={columnsResource}
                  data={(() => {
                    const flattenedArray = data?.resourcePermissions.flatMap(
                      (resource) =>
                        resource.permissions.map((permission) => ({
                          resource: resource.resource,
                          permission,
                        })),
                    );
                    return flattenedArray || [];
                  })()}
                />
              </div>
            </TabsContent>
          </Tabs>
        ) : (
          <div className="flex flex-col gap-4 pt-2">
            <Button onClick={() => openRoleAddPermissionSheet("resource")}>
              Adicionar permissão de recurso
            </Button>
            <DataTable
              columns={columnsResource2}
              data={(() => {
                const flattenedArray = data?.resourcePermissions.flatMap(
                  (resource) =>
                    resource.permissions.map((permission) => ({
                      resource: resource.resource,
                      permission,
                    })),
                );
                return flattenedArray || [];
              })()}
            />
          </div>
        )}
      </DialogContent>
    </Dialog>
  );
}

RolePermissionsDialog.displayName = "RolePermissionsDialog";

export {
  RolePermissionsDialog,
  RolePermissionsDialogProvider,
  useRolePermissionsDialogContext,
};
