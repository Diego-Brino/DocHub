import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { ArrowLeft, GitBranchPlus } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { Separator } from "@/components/ui/separator.tsx";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { ServiceCard } from "@/features/flows/service-card/service-card.tsx";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form.tsx";
import { Input } from "@/components/custom/input.tsx";
import { useState } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";

const schemaProcess = z.object({
  description: z.string().min(1, "Nome é obrigatório"),
});

function GroupFlows() {
  const { token } = useAuthContext();

  const { id } = useParams();

  const { data: dataGroup } = useGetGroup(Number(id));

  const { data: dataServices } = useQuery(
    ["services"],
    async (): Promise<
      {
        id: number;
        description: string;
      }[]
    > => {
      const response = await axiosClient.get(`/services`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const navigate = useNavigate();

  const [openNewFlowModal, setOpenNewFlowModal] = useState(false);

  const formProcess = useForm<z.infer<typeof schemaProcess>>({
    resolver: zodResolver(schemaProcess),
    defaultValues: {
      description: "",
    },
  });

  const { mutateAsync: mutateAsyncPostProcess, isLoading: isLoading2 } =
    useMutation({
      mutationFn: ({ flowId }: { flowId: number }) =>
        axiosClient.post(
          `/processes`,
          {
            serviceId: flowId,
            groupId: id,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        ),
      onSuccess: () => {
        queryClient.invalidateQueries(["services"]);
      },
    });

  const { mutateAsync: mutateAsyncPostServices, isLoading: isLoading1 } =
    useMutation({
      mutationFn: ({ description }: { description: string }) =>
        axiosClient.post(
          `/services`,
          {
            description,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        ),
      onSuccess: (data: { data: number }) => {
        queryClient.invalidateQueries(["services"]);
        toast.success("Fluxo criado com sucesso");
        mutateAsyncPostProcess({ flowId: data.data });
      },
    });

  const submitFormProcess = (values: z.infer<typeof schemaProcess>) => {
    mutateAsyncPostServices(values).then(() => {
      setOpenNewFlowModal(false);
    });
  };

  return (
    <>
      <div className="flex gap-4 flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
        <GroupToolbarProvider>
          <div className="flex gap-4 items-center">
            <Button
              size={`icon`}
              onClick={() => {
                navigate(-1);
              }}
            >
              <ArrowLeft />
            </Button>
            <h1 className="text-xl md:text-3xl font-semibold">
              {dataGroup?.name}
            </h1>
            <Separator orientation="vertical" />
          </div>
          <GroupToolbar
            currentPath={[]}
            setCurrentPath={() => {}}
            buttons={
              <>
                <Button
                  className="gap-2"
                  onClick={() => {
                    setOpenNewFlowModal(true);
                  }}
                >
                  <GitBranchPlus />
                  Criar Novo Fluxo
                </Button>
              </>
            }
          />
          <div className="flex flex-col items-start justify-start w-full gap-4 overflow-y-scroll h-full">
            {dataServices?.map((service) => <ServiceCard service={service} />)}
          </div>
        </GroupToolbarProvider>
      </div>
      <Dialog
        open={openNewFlowModal}
        onOpenChange={(open) => {
          setOpenNewFlowModal(open);
        }}
      >
        <DialogContent>
          <DialogHeader>
            <DialogTitle>Novo Fluxo</DialogTitle>
            <DialogDescription>
              Crie um novo fluxo para gerenciar seus arquivos.
            </DialogDescription>
          </DialogHeader>
          <Form {...formProcess}>
            <form onSubmit={formProcess.handleSubmit(submitFormProcess)}>
              <div className="pb-6 space-y-4">
                <FormField
                  control={formProcess.control}
                  name="description"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome do Fluxo</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={
                            formProcess.formState.errors.description?.message
                          }
                        />
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <DialogFooter>
                <Button
                  loading={isLoading1 || isLoading2}
                  disabled={isLoading1 || isLoading2}
                >
                  Confirmar
                </Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </>
  );
}

GroupFlows.displayName = "GroupFlows";

export { GroupFlows };
