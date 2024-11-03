import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import { ArrowRight, EditIcon, TextIcon, TrashIcon } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { useDeleteGroup } from "@/services/groups/use-delete-group.ts";
import { motion } from "framer-motion";
import { useGroupSheetContext } from "@/features/groups/group-sheet/group-sheet.tsx";
import { useGroupDeleteConfirmationAlert } from "@/features/groups/group-delete-confirmation-alert/group-delete-confirmation-alert.tsx";
import { useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { ScrollArea } from "@/components/ui/scroll-area.tsx";
import { useGetGroupHistory } from "@/services/groups/use-get-group-history.ts";

type GroupCardProps = {
  group: {
    id: number;
    name: string;
    description: string;
    groupUrl: string;
  };
};

function GroupCard({
  group: { id, name, description, groupUrl },
}: GroupCardProps) {
  const { mutate: mutateDelete } = useDeleteGroup();
  const { open } = useGroupDeleteConfirmationAlert();
  const { open: openGroupSheet } = useGroupSheetContext();

  const handleDelete = () => {
    mutateDelete(id);
  };

  const [openHistory, setOpenHistory] = useState(false);

  const { data } = useGetGroupHistory(id);

  return (
    <>
      <motion.div
        layout
        animate={{ opacity: 1 }}
        initial={{ opacity: 0 }}
        exit={{ opacity: 0 }}
      >
        <Card className="">
          <CardHeader className="flex flex-row justify-between">
            <div className="flex flex-col gap-2">
              <CardTitle>{name}</CardTitle>
              <CardDescription>{description}</CardDescription>
            </div>
          </CardHeader>
          <CardContent>
            <div className="flex w-full h-[220px]">
              <img
                src={groupUrl}
                alt={name}
                className="w-full object-scale-down"
              />
            </div>
          </CardContent>
          <CardFooter className="flex justify-end items-center gap-2">
            <Button
              variant="outline"
              size="icon"
              onClick={() => setOpenHistory(!openHistory)}
            >
              <TextIcon className="size-5" />
            </Button>
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
            <Button className="gap-2" variant="outline" onClick={() => {}}>
              Acessar
              <ArrowRight className="size-5" />
            </Button>
          </CardFooter>
        </Card>
      </motion.div>
      <Dialog open={openHistory} onOpenChange={(open) => setOpenHistory(open)}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Histórico de alterações</DialogTitle>
            <DialogDescription>
              Veja todas as alterações realizadas neste grupo.
            </DialogDescription>
          </DialogHeader>
          <ScrollArea>
            {data?.map((history) => (
              <div key={history.id} className="flex flex-col gap-2">
                <p className="text-muted-foreground">{history.actionType}</p>
                <p>{history.description}</p>
              </div>
            ))}
          </ScrollArea>
        </DialogContent>
      </Dialog>
    </>
  );
}

GroupCard.displayName = "GroupCard";

export { GroupCard };
