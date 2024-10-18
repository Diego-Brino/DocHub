import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import { EditIcon, TrashIcon } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { Avatar, AvatarImage } from "@/components/ui/avatar.tsx";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
import { RoleDeleteConfirmationAlert } from "@/features/roles/role-delete-confirmation-alert/role-delete-confirmation-alert.tsx";
import { motion } from "framer-motion";
import { useUserDeleteConfirmationAlertContext } from "@/features/users/user-delete-confirmation-alert/user-delete-confirmation-alert.tsx";
import { useUserSheetContext } from "@/features/users/user-sheet/user-sheet.tsx";
import { useDeleteProfile } from "@/services/profiles/use-delete-profile.ts";

type UserCardProps = {
  user: {
    id: number;
    name: string;
    username: string;
    email: string;
    avatarUrl: string;
  };
};

function UserCard({
  user: { id, name, username, email, avatarUrl },
}: UserCardProps) {
  const { mutate: mutateDelete } = useDeleteProfile({ profileId: id });

  const { open: openUserSheet } = useUserSheetContext();

  const { open } = useUserDeleteConfirmationAlertContext();

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
        <Card className="h-[194px]">
          <CardHeader className="flex flex-row justify-between">
            <div className="flex flex-col gap-2">
              <CardTitle>{name}</CardTitle>
              <CardDescription>{email}</CardDescription>
              <CardDescription>{username}</CardDescription>
            </div>
            <Avatar>
              <AvatarImage src={avatarUrl} alt={name} />
            </Avatar>
          </CardHeader>
          <CardContent>
            <div className="flex justify-end items-center">
              <div className="flex items-center gap-2">
                <Tooltip>
                  <TooltipTrigger asChild>
                    <Button
                      variant="outline"
                      size="icon"
                      onClick={() => {
                        openUserSheet(id);
                      }}
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

UserCard.displayName = "UserCard";

export { UserCard };
