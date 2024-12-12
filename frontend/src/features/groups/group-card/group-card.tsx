import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import {
  EditIcon,
  Files,
  FolderGit2,
  GitPullRequest,
  TextIcon,
  TrashIcon,
} from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { useDeleteGroup } from "@/services/groups/use-delete-group.ts";
import { motion } from "framer-motion";
import { useGroupSheetContext } from "@/features/groups/group-sheet/group-sheet.tsx";
import { useGroupDeleteConfirmationAlert } from "@/features/groups/group-delete-confirmation-alert/group-delete-confirmation-alert.tsx";
import { Fragment, useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { ScrollArea } from "@/components/ui/scroll-area.tsx";
import { useGetGroupHistory } from "@/services/groups/use-get-group-history.ts";
import { Separator } from "@/components/ui/separator.tsx";
import { useNavigate } from "react-router-dom";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";

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
  const navigate = useNavigate();

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
        <Card>
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
            <Button
              className="gap-2"
              variant="outline"
              size="icon"
              onClick={() => {
                navigate(`/groups/${id}/flows`);
              }}
            >
              <Tooltip>
                <TooltipTrigger>
                  <GitPullRequest className="size-5" />
                </TooltipTrigger>
                <TooltipContent>
                  <p>Gerenciar Fluxos</p>
                </TooltipContent>
              </Tooltip>
            </Button>
            <Button
              className="gap-2"
              variant="outline"
              size="icon"
              onClick={() => {
                navigate(`/groups/${id}/use-flows`);
              }}
            >
              <Tooltip>
                <TooltipTrigger>
                  <FolderGit2 className="size-5" />
                </TooltipTrigger>
                <TooltipContent>
                  <p>Meus Fluxos</p>
                </TooltipContent>
              </Tooltip>
            </Button>
            <Button
              className="gap-2"
              variant="outline"
              size="icon"
              onClick={() => {
                navigate(`/groups/${id}/files`);
              }}
            >
              <Tooltip>
                <TooltipTrigger>
                  <Files className="size-5" />
                </TooltipTrigger>
                <TooltipContent>
                  <p>Gerenciar Arquivos</p>
                </TooltipContent>
              </Tooltip>
            </Button>
          </CardFooter>
        </Card>
      </motion.div>
      <Dialog open={openHistory} onOpenChange={(open) => setOpenHistory(open)}>
        <DialogContent className="h-[500px] max-h-[500px] overflow-y-hidden flex flex-col">
          <DialogHeader>
            <DialogTitle>Histórico de alterações</DialogTitle>
            <DialogDescription>
              Veja todas as alterações realizadas neste grupo.
            </DialogDescription>
          </DialogHeader>
          {data && data?.length > 0 ? (
            <ScrollArea className="w-full h-96">
              {data.map((history) => (
                <Fragment key={history.id}>
                  <div className="flex flex-col gap-2">
                    <div className="flex justify-between">
                      <p className="text-muted-foreground">
                        {history.actionUser}
                      </p>
                      <p className="text-muted-foreground">
                        {history.actionDate}
                      </p>
                    </div>
                    <p>{history.description}</p>
                  </div>
                  <Separator className="my-2" />
                </Fragment>
              ))}
            </ScrollArea>
          ) : (
            <div className="h-full w-full flex justify-center items-center">
              <p className="text-muted-foreground">
                Nenhuma alteração encontrada.
              </p>
            </div>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
}

GroupCard.displayName = "GroupCard";

export { GroupCard };
