import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import { usePatchRoleStatus } from "@/services/roles/use-patch-role-status.ts";
import { Badge } from "@/components/ui/badge.tsx";
import { cn } from "@/lib/utils.ts";
import { EditIcon, TrashIcon, Users } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { useGetRoleUsers } from "@/services/roles/use-get-role-users.ts";
import { Avatar, AvatarImage } from "@/components/ui/avatar.tsx";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
import { useDeleteRole } from "@/services/roles/use-delete-role.ts";
import {
  RoleDeleteConfirmationAlert,
  useRoleDeleteConfirmationAlertContext,
} from "@/features/roles/role-delete-confirmation-alert/role-delete-confirmation-alert.tsx";
import { motion } from "framer-motion";
import { useRoleSheetContext } from "@/features/roles/role-sheet/role-sheet.tsx";
import { useRoleUsersDialogContext } from "@/features/roles/role-users-dialog/role-users-dialog.tsx";

type RoleCardProps = {
  role: {
    id: number;
    name: string;
    description: string;
    color: string;
    status: string;
    systemPermissions: { id: number; description: string }[];
  };
};

function RoleCard({
  role: { id, name, description, color, status },
}: RoleCardProps) {
  const { mutate: mutatePatchStatus } = usePatchRoleStatus({ roleId: id });
  const { mutate: mutateDelete } = useDeleteRole({ roleId: id });
  const { data, isLoading } = useGetRoleUsers({ roleId: id });

  const { open } = useRoleDeleteConfirmationAlertContext();
  const { open: openRoleSheet } = useRoleSheetContext();
  const { open: openRoleUsersDialog } = useRoleUsersDialogContext();

  const toggleStatus = () => {
    mutatePatchStatus({
      roleStatus: status === "ATIVO" ? "INACTIVE" : "ACTIVE",
    });
  };

  const handleDelete = () => {
    mutateDelete();
  };

  return (
    <>
      <motion.div
        layout
        animate={{ opacity: 1 }}
        initial={{ opacity: 0 }}
        exit={{ opacity: 0 }}
      >
        <Card
          className="h-[167px]"
          style={{
            borderLeft: `16px solid ${color}`,
          }}
        >
          <CardHeader className="flex flex-row justify-between">
            <div className="flex flex-col gap-2">
              <CardTitle>{name}</CardTitle>
              <CardDescription>{description}</CardDescription>
            </div>
            <Badge
              variant="outline"
              className="cursor-pointer h-min px-2 py-1 hover:bg-accent"
              onClick={toggleStatus}
            >
              {status}
              <div
                className={cn(
                  "w-2 h-2 rounded-full ml-2",
                  status === "ATIVO" ? "bg-green-500" : "bg-red-500",
                )}
              />
            </Badge>
          </CardHeader>
          <CardContent>
            <div className="flex justify-between items-center">
              <div className="flex -space-x-3">
                {!isLoading &&
                  data &&
                  data.map((user) => (
                    <Tooltip key={user.id}>
                      <TooltipTrigger>
                        <Avatar>
                          <AvatarImage src={user.avatarUrl} alt={user.name} />
                        </Avatar>
                      </TooltipTrigger>
                      <TooltipContent>{user.name}</TooltipContent>
                    </Tooltip>
                  ))}
              </div>
              <div className="flex items-center gap-2">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => openRoleUsersDialog(id)}
                    >
                      <Users className="size-5" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>Usuários</TooltipContent>
                </Tooltip>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => openRoleSheet(id)}
                    >
                      <EditIcon className="size-5" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>Editar</TooltipContent>
                </Tooltip>
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => open(handleDelete)}
                    >
                      <TrashIcon className="size-5" />
                    </Button>
                  </TooltipTrigger>
                  <TooltipContent>Excluir</TooltipContent>
                </Tooltip>
              </div>
            </div>
          </CardContent>
        </Card>
      </motion.div>
      <RoleDeleteConfirmationAlert />
    </>
  );
}

RoleCard.displayName = "RoleCard";

export { RoleCard };
