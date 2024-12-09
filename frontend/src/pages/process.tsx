import { useNavigate, useParams } from "react-router-dom";
import { useAuthContext } from "@/contexts/auth";
import { Button } from "@/components/custom/button.tsx";
import { ArrowLeft, PackagePlus, Workflow } from "lucide-react";
import { Separator } from "@/components/ui/separator.tsx";
import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { Process } from "@/pages/flow.tsx";
import {
  addEdge,
  ReactFlow,
  useEdgesState,
  useNodesState,
} from "@xyflow/react";
import "@xyflow/react/dist/style.css";
import { useCallback, useEffect, useState } from "react";
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
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select.tsx";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

const schema = z.object({
  order: z.string().min(1, "Ordem é obrigatória"),
  time: z.string().min(1, "Tempo é obrigatório"),
  processId: z.number().int().positive("Processo é obrigatório"),
  description: z.string().min(1, "Nome é obrigatório"),
});

function ProcessPage() {
  const { token } = useAuthContext();

  const { id, flowId, processId } = useParams();

  const [openNewFlowModal, setOpenNewFlowModal] = useState(false);

  const { data: dataGroup } = useGetGroup(Number(id));

  const navigate = useNavigate();

  const { data: dataService } = useQuery(
    ["services", flowId],
    async (): Promise<{
      id: number;
      description: string;
    }> => {
      const response = await axiosClient.get(`/services/${flowId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { data: dataProcess } = useQuery(
    ["processes", processId],
    async (): Promise<Process> => {
      const response = await axiosClient.get(`/processes/${processId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncPostFlow, isLoading } = useMutation(
    async (data: {
      order: number;
      time: number;
      processId: number;
      activityId: number;
    }) => {
      const response = await axiosClient.post(`/flows`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncPostActivity } = useMutation(
    async (data: { description: string }) => {
      const response = await axiosClient.post(`/activities`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncPostResponseFlow } = useMutation(
    async (data: {
      flowId: number;
      responseId: number;
      destinationFlowId: number;
    }) => {
      console.log("Sending data", data);
      const response = await axiosClient.post(`/response-flows`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncPostResponse } = useMutation(
    async (data: { description: string }) => {
      const response = await axiosClient.post(`/responses`, data, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const getMaxFlowsOrder = (process: Process) => {
    const val = process.flows.reduce((acc, flow) => {
      if (flow.order > acc) {
        return flow.order;
      }
      return acc;
    }, 0);

    return val + 1;
  };

  const initialNodes: {
    id: string;
    position: { x: number; y: number };
    data: { label: string };
  }[] = [];

  const initialEdges: {
    id: string;
    source: string;
    target: string;
  }[] = [];

  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

  const onConnect = useCallback(
    (params: any) => {
      console.log(nodes);
      const targetLabel = nodes.find((node) => node.id === params.target)?.data;
      console.log(targetLabel);
      mutateAsyncPostResponse({
        description: targetLabel?.label as string,
      }).then((responseId) => {
        mutateAsyncPostResponseFlow({
          flowId: Number(params.source),
          responseId: responseId,
          destinationFlowId: Number(params.target),
        }).then(() => {
          console.log("response flow created");
          console.log(params);
          setEdges((eds) => addEdge(params, eds));
          queryClient.invalidateQueries(["services", flowId]);
        });
      });
    },
    [setEdges, dataProcess, dataService],
  );

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      order: "1",
      time: "1",
      processId: Number(processId),
      description: "",
    },
  });

  const submitForm = (data: z.infer<typeof schema>) => {
    mutateAsyncPostActivity({
      description: data.description,
    }).then((activityId) => {
      mutateAsyncPostFlow({
        order: Number(data.order),
        time: Number(data.time),
        processId: data.processId,
        activityId: activityId,
      }).then(() => {
        toast.success("Etapa criada com sucesso");
        setOpenNewFlowModal(false);
        form.reset();
        queryClient.invalidateQueries(["processes"]);
        queryClient.invalidateQueries(["services"]);
      });
    });
  };

  useEffect(() => {
    if (!dataProcess) {
      return;
    }

    for (let i = 0; i < dataProcess?.flows.length; i++) {
      console.log("HAHA", dataProcess?.flows[i]);
      const flow = dataProcess?.flows[i];

      setNodes((nodes) => [
        ...nodes,
        {
          id: flow.id.toString(),
          position: { x: 0, y: 100 * (i + 1) },
          data: { label: flow?.activity.description },
        },
      ]);
      setEdges((edges) => [
        ...edges,
        ...flow?.responseFlows.map((response) => ({
          id: `${flow.id}-${response.flow.id}`,
          source: flow.id.toString(),
          target: response.destinationFlow?.id?.toString(),
        })),
      ]);
    }
  }, [dataService, dataProcess]);

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
            <h2 className="text-lg font-semibold">
              {dataService?.description}
            </h2>
            <Separator orientation="vertical" />
            <h2 className="text-lg font-semibold">Processo</h2>
          </div>
          <GroupToolbar
            currentPath={[]}
            setCurrentPath={() => {}}
            buttons={
              <>
                <Button
                  disabled={isLoading}
                  loading={isLoading}
                  className="gap-2"
                  onClick={() => {
                    setOpenNewFlowModal(true);
                  }}
                >
                  <Workflow />
                  Criar Nova Etapa
                </Button>
              </>
            }
          />
          <div className="flex flex-col items-start justify-center w-full pb-4 gap-2 h-full">
            <ReactFlow
              nodes={nodes}
              edges={edges}
              onNodesChange={onNodesChange}
              onEdgesChange={onEdgesChange}
              onConnect={onConnect}
            />
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
            <DialogTitle>Nova etapa</DialogTitle>
            <DialogDescription>
              Adicione uma nova etapa ao processo
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(submitForm)}>
              <div className="pb-6 space-y-4">
                <FormField
                  control={form.control}
                  name="description"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Nome</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={form.formState.errors.description?.message}
                        />
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="order"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Ordem</FormLabel>
                      <FormControl>
                        <Select
                          onValueChange={field.onChange}
                          value={field.value.toString()}
                        >
                          <SelectTrigger>
                            <SelectValue
                              placeholder={"Selecione a ordem da etapa"}
                            />
                          </SelectTrigger>
                          <SelectContent>
                            {dataProcess &&
                              Array.from(
                                { length: getMaxFlowsOrder(dataProcess) },
                                (_, i) => (
                                  <SelectItem
                                    key={i + 1}
                                    value={(i + 1).toString()}
                                  >
                                    {i + 1}
                                  </SelectItem>
                                ),
                              )}
                          </SelectContent>
                        </Select>
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="time"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Tempo em dias</FormLabel>
                      <FormControl>
                        <Input
                          {...field}
                          error={form.formState.errors.time?.message}
                        />
                      </FormControl>
                      <FormDescription />
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <DialogFooter>
                <Button loading={isLoading} disabled={isLoading}>
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

ProcessPage.displayName = "ProcessPage";

export { ProcessPage };
