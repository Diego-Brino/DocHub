import { ArrowRight, CircleOff, Edit, PanelBottomOpen } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { Card } from "@/components/ui/card.tsx";
import { useNavigate, useParams } from "react-router-dom";
import { Process } from "@/pages/flow.tsx";
import { Separator } from "@/components/ui/separator.tsx";
import { useMutation } from "react-query";
import axiosClient from "@/lib/axios";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";
import { useAuthContext } from "@/contexts/auth";
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

function formatDateBrazil(date: Date) {
  const brasilOffset = -3;
  const localTime = date.getTime();
  const offsetInMs = brasilOffset * 60 * 60 * 1000;

  const brasilDate = new Date(localTime + offsetInMs);

  const day = String(brasilDate.getUTCDate()).padStart(2, "0");
  const month = String(brasilDate.getUTCMonth() + 1).padStart(2, "0");
  const year = brasilDate.getUTCFullYear();

  return `${day}/${month}/${year}`;
}

function ProcessCard({ process, order }: { process: Process; order: number }) {
  const navigate = useNavigate();

  const { token } = useAuthContext();

  const { id, flowId } = useParams();

  const { mutateAsync: finishProcess } = useMutation(
    async ({ idProcess, endDate }: { idProcess: number; endDate: string }) => {
      const response = await axiosClient.patch(
        `/processes/${idProcess}/end-date`,
        { endDate },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["services"]);
        toast.success("Processo finalizado com sucesso");
      },
    },
  );

  const [isOpen, setIsOpen] = useState(false);

  const {
    mutateAsync: mutateAsyncPatchProcessInProgress,
    isLoading: isLoadingPatchProcessInProgress,
  } = useMutation({
    mutationFn: () =>
      axiosClient.patch(
        `/processes/${flowId}/in-progress`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      ),
    onSuccess: () => {
      queryClient.invalidateQueries(["services"]);
      toast.success("Processo retomado com sucesso");
    },
  });

  const {
    mutateAsync: mutateAsyncPostRequest,
    isLoading: isLoadingPostRequest,
  } = useMutation({
    mutationFn: ({ processId }: { processId: number }) =>
      axiosClient.post(
        `/requests`,
        {
          processId,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      ),
    onSuccess: () => {
      queryClient.invalidateQueries(["services"]);
      toast.success("Processo iniciado com sucesso");
    },
  });

  const [isRequestOpen, setIsRequestOpen] = useState(false);

  return (
    <>
      <Card key={process.id} className={"w-full p-4"}>
        <div className="flex gap-4 items-center justify-between">
          <div className="flex gap-2 items-center justify-start">
            <h2 className="text-lg font-semibold">{"Processo - " + order}</h2>
            <Separator orientation="vertical" />
            <p className="text-sm text-muted-foreground">
              Criado em: {process.startDate.split(" ")[0]}
            </p>
            {process.endDate && (
              <p className="text-sm text-muted-foreground">
                Finalizado em: {process.endDate.split(" ")[0]}
              </p>
            )}
          </div>
          <div className="flex gap-4 items-center">
            {process.endDate === "" ? (
              <Button
                disabled={process.endDate !== ""}
                variant="destructive"
                className="gap-2"
                onClick={() => {
                  setIsOpen(true);
                }}
              >
                <CircleOff className="size-5" />
                Finalizar
              </Button>
            ) : (
              <Button
                variant={"outline"}
                disabled={isLoadingPatchProcessInProgress}
                className="gap-2"
                onClick={() => {
                  mutateAsyncPatchProcessInProgress();
                }}
              >
                <PanelBottomOpen className="size-5" />
                Retomar
              </Button>
            )}
            <Button
              className="flex gap-2"
              onClick={() => {
                navigate(
                  `/groups/${id}/flows/${flowId}/processes/${process.id}`,
                );
              }}
            >
              <Edit />
              Editar
            </Button>
            <Button
              className="gap-2"
              disabled={isLoadingPostRequest}
              loading={isLoadingPostRequest}
              onClick={() => {
                setIsRequestOpen(true);
              }}
            >
              Iniciar
              <ArrowRight />
            </Button>
          </div>
        </div>
      </Card>
      <AlertDialog
        open={isOpen}
        onOpenChange={() => {
          setIsOpen(false);
        }}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Finalização</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja finalizar este processo?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={() =>
                finishProcess({
                  idProcess: process.id,
                  endDate: formatDateBrazil(new Date()),
                })
              }
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <AlertDialog
        open={isOpen}
        onOpenChange={() => {
          setIsOpen(false);
        }}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Finalização</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja finalizar este processo?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={() =>
                finishProcess({
                  idProcess: process.id,
                  endDate: formatDateBrazil(new Date()),
                })
              }
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <AlertDialog
        open={isRequestOpen}
        onOpenChange={() => {
          setIsRequestOpen(false);
        }}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Requisição</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja iniciar este processo? Depois de
              iniciado não será possível finalizar.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={() =>
                mutateAsyncPostRequest({
                  processId: process.id,
                })
              }
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}

export { ProcessCard };
