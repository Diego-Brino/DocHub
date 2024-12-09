import { AlertTriangle, ArrowRight, Trash } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { Card } from "@/components/ui/card.tsx";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
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
import { useState } from "react";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";
import { useNavigate, useParams } from "react-router-dom";

function ServiceCard({
  service,
}: {
  service: {
    id: number;
    description: string;
  };
}) {
  const { token } = useAuthContext();

  const navigate = useNavigate();

  const { id } = useParams();

  const { mutateAsync } = useMutation({
    mutationFn: () => {
      return axiosClient.delete(`/services/${service.id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["services"]);
      toast.success("Serviço deletado com sucesso");
    },
  });

  const { data: dataServiceProcesses } = useQuery(
    [service.id, "processes"],
    async (): Promise<
      {
        id: number;
      }[]
    > => {
      const response = await axiosClient.get(
        `/services/${service.id}/processes`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    },
  );

  const [isOpen, setIsOpen] = useState(false);

  return (
    <>
      <Card key={service.id} className={"w-full p-4"}>
        <div className="flex gap-4 items-center justify-between">
          <h2 className="text-lg font-semibold">{service.description}</h2>
          <div className="flex gap-4 items-center">
            {dataServiceProcesses && dataServiceProcesses.length == 0 && (
              <Tooltip>
                <TooltipTrigger>
                  <AlertTriangle />
                </TooltipTrigger>
                <TooltipContent>
                  Nenhum processo cadastrado para este fluxo
                </TooltipContent>
              </Tooltip>
            )}
            <Button
              size={`icon`}
              onClick={() => setIsOpen(true)}
              variant="destructive"
            >
              <Trash />
            </Button>
            <Button
              className="gap-2"
              onClick={() => {
                navigate(`/groups/${id}/flows/${service.id}`);
              }}
            >
              Acessar
              <ArrowRight />
            </Button>
          </div>
        </div>
      </Card>
      <AlertDialog open={isOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>
              Tem certeza que deseja deletar este fluxo?
            </AlertDialogTitle>
            <AlertDialogDescription>
              Esta ação é irreversível e não pode ser desfeita.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel
              onClick={() => {
                setIsOpen(false);
              }}
            >
              Cancelar
            </AlertDialogCancel>
            <AlertDialogAction
              onClick={async () => {
                await mutateAsync();
                setIsOpen(false);
              }}
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}

export { ServiceCard };
