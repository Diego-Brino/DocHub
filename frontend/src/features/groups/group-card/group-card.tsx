import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import { EditIcon, TrashIcon } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { useDeleteGroup } from "@/services/groups/use-delete-group.ts";
import { motion } from "framer-motion";
import { useGroupSheetContext } from "@/features/groups/group-sheet/group-sheet.tsx";
import { useGroupDeleteConfirmationAlert } from "@/features/groups/group-delete-confirmation-alert/group-delete-confirmation-alert.tsx";

type GroupCardProps = {
  group: {
    id: number;
    name: string;
    description: string;
    groupUrl: string;
  };
};

function GroupCard({ group: { id, name, description } }: GroupCardProps) {
  const { mutate: mutateDelete } = useDeleteGroup();
  const { open } = useGroupDeleteConfirmationAlert();
  const { open: openGroupSheet } = useGroupSheetContext();

  const handleDelete = () => {
    mutateDelete(id);
  };

  return (
    <>
      <motion.div
        layout
        animate={{ opacity: 1 }}
        initial={{ opacity: 0 }}
        exit={{ opacity: 0 }}
      >
        <Card className="h-[167px]">
          <CardHeader className="flex flex-row justify-between">
            <div className="flex flex-col gap-2">
              <CardTitle>{name}</CardTitle>
              <CardDescription>{description}</CardDescription>
            </div>
          </CardHeader>
          <CardContent>
            <div className="flex justify-between items-center">
              <div className="flex items-center gap-2">
                <Button
                  variant="outline"
                  size="icon"
                  onClick={() => openGroupSheet(id)}
                >
                  <EditIcon className="size-5" />
                </Button>
                <Button
                  variant="outline"
                  size="icon"
                  onClick={() => open(handleDelete)}
                >
                  <TrashIcon className="size-5" />
                </Button>
              </div>
            </div>
          </CardContent>
        </Card>
      </motion.div>
    </>
  );
}

GroupCard.displayName = "GroupCard";

export { GroupCard };
