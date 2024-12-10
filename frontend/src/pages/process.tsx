import { useNavigate, useParams } from "react-router-dom";
import { useAuthContext } from "@/contexts/auth";
import { Button } from "@/components/custom/button.tsx";
import { ArrowLeft, Workflow } from "lucide-react";
import { Separator } from "@/components/ui/separator.tsx";
import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { Process, ResponseFlow } from "@/pages/flow.tsx";
import {
  NodeMouseHandler,
  ReactFlow, ReactFlowInstance,
  useEdgesState,
  useNodesState,
} from "@xyflow/react";
import "@xyflow/react/dist/style.css";
import { useEffect, useState } from "react";
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
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { DataTable } from "@/components/custom/data-table.tsx";
import { ColumnDef } from "@tanstack/react-table";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover.tsx";

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

  const { data: dataResponseFlows } = useQuery(
    ["response-flows"],
    async (): Promise<ResponseFlow> => {
      const response = await axiosClient.get(`/response-flows`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  useEffect(() => {
    console.log(dataResponseFlows);
  }, [dataResponseFlows]);

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

  const { mutateAsync: mutateAsyncDeleteResponseFlow } = useMutation(
    async (data: { idFlow: number; idResponse: number }) => {
      const response = await axiosClient.delete(
        `/response-flows/${data.idFlow}/${data.idResponse}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncDeleteFlow } = useMutation(
    async (data: { idFlow: number }) => {
      const response = await axiosClient.delete(
        `/flows/${data.idFlow}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    },
  );

  const [nodes, setNodes, onNodesChange] = useNodesState(initialNodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(initialEdges);

  const [instance, setInstance] = useState<ReactFlowInstance | null>(null);

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

  const [mountTrigger, setMountTrigger] = useState(false);

  useEffect(() => {
    setNodes([])
    setEdges([])

    if (!dataProcess) {
      return;
    }

    for (let i = 0; i < dataProcess?.flows.length; i++) {
      const flow = dataProcess?.flows[i];

      setNodes((nodes) => [
        ...nodes,
        {
          id: flow.id.toString(),
          position: { x: 100 * i, y: 100 * flow?.order},
          data: { label: flow?.order + " - " + flow?.activity.description }
        },
      ]);

      flow.responseFlows.forEach((responseFlow) => {
        setEdges((edges) => [
          ...edges,
          {
            id: responseFlow.response.id.toString(),
            source: flow.id.toString(),
            target: responseFlow.destinationFlow?.id.toString() as string,
          }
        ]);
      });
    }

    console.log(nodes);
  }, [dataService, mountTrigger]);

  const [selectedNode, setSelectedNode] = useState<{
    id: string;
    position: { x: number; y: number };
    data: { label: string };
  } | null>(null);
  const [isBottomSheetOpen, setBottomSheetOpen] = useState(false);

  const handleNodeClick: NodeMouseHandler<{
    id: string;
    position: { x: number; y: number };
    data: { label: string };
  }> = (_event, node) => {
    setSelectedNode(node);
    setBottomSheetOpen(true);
  };

  const columns: ColumnDef<{
    name: string;
    order: string;
    time: string;
  }>[] = [
    {
      header: "Nome",
      accessorKey: "name",
      cell: ({ row }) => <p className="text-nowrap">{row.getValue("name")}</p>,
    },
    {
      header: "Ordem",
      accessorKey: "order",
      cell: ({ row }) => <p className="text-nowrap">{row.getValue("order")}</p>,
    },
    {
      header: "Tempo",
      accessorKey: "time",
      cell: ({ row }) => <p className="text-nowrap">{row.getValue("time")}</p>,
    },
    {
      header: "Ações",
      accessorKey: "actions",
      cell: ({ row }) => (
        <div className="flex gap-2">
          <Button
            variant="ghost"
            onClick={() => {
              const connectedFlow = dataProcess?.flows.find(
                (flow) => flow.id.toString() === selectedNode?.id,
              );

              const responseFlow = connectedFlow?.responseFlows.find(
                (response) =>
                  response.destinationFlow?.activity.description.toString() ===
                  row.original.name,
              );

              if (!responseFlow) {
                toast.error("ID de resposta não encontrado.");
                return;
              }

              mutateAsyncDeleteResponseFlow({
                idFlow: Number(selectedNode?.id),
                idResponse: responseFlow.response.id,
              }).then(() => {
                toast.success("Etapa desvinculada com sucesso");
                queryClient.invalidateQueries(["processes"]);
                queryClient.invalidateQueries(["services"]);
                setMountTrigger(!mountTrigger);
                setBottomSheetOpen(false);
              });
            }}
          >
            Excluir
          </Button>
        </div>
      ),
    },
  ];

  const [connectedFlows, setConnectedFlows] = useState<
    {
      name: string;
      order: string;
      time: string;
    }[]
  >([]);

  useEffect(() => {
    if (!selectedNode) {
      return;
    }

    const connectedFlows = edges
      .filter((edge) => edge.source === selectedNode.id)
      .map((edge) => {
        const node = nodes.find((node) => node.id === edge.target);
        return {
          name: node?.data.label as string,
          order: dataProcess?.flows
            .find((flow) => flow.id.toString() === node?.id)
            ?.order.toString(),
          time: dataProcess?.flows
            .find((flow) => flow.id.toString() === node?.id)
            ?.time.toString(),
        };
      });

    const uniqueFlows = connectedFlows.filter(
      (flow, index, self) =>
        index ===
        self.findIndex(
          (t) =>
            t.name === flow.name &&
            t.order === flow.order &&
            t.time === flow.time,
        ),
    );

    setConnectedFlows(uniqueFlows);
  }, [selectedNode]);

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
              onNodeClick={handleNodeClick}
              onInit={(instance) => setInstance(instance)}
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
      <Sheet
        open={isBottomSheetOpen}
        onOpenChange={(open) => setBottomSheetOpen(open)}
      >
        <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
          <SheetHeader>
            <SheetTitle>Etapa</SheetTitle>
            <SheetDescription>{selectedNode?.data.label}</SheetDescription>
          </SheetHeader>
          <div className="pt-4">
            <DataTable columns={columns} data={connectedFlows} />
          </div>
          <div className="w-full flex flex-col gap-2">
            <Popover>
              <PopoverTrigger className="w-full">
                <Button className="w-full flex gap-2">
                  <Workflow />
                  Vincular nova etapa
                </Button>
              </PopoverTrigger>
              <PopoverContent>
                {dataProcess?.flows
                  .filter(
                    (f) =>
                      f.id != selectedNode?.id &&
                      (f.order === dataProcess?.flows.find((flow) => flow.id.toString() === selectedNode?.id,)?.order + 1) &&
                      !connectedFlows.find((flow) => flow.name === f.activity.description),
                  )
                  .map((flow) => (
                    <Button
                      variant="ghost"
                      className="w-full"
                      onClick={() => {
                        mutateAsyncPostResponse({
                          description: flow.activity.description,
                        }).then((response) => {
                          mutateAsyncPostResponseFlow({
                            flowId: Number(selectedNode?.id),
                            responseId: response,
                            destinationFlowId: flow.id,
                          }).then(() => {
                            toast.success("Etapa vinculada com sucesso");
                            queryClient.invalidateQueries(["processes"]);
                            queryClient.invalidateQueries(["services"]);
                            setBottomSheetOpen(false);
                          });
                        });
                      }}
                    >
                      {flow.activity.description}
                    </Button>
                  ))}
              </PopoverContent>
            </Popover>
            <Button variant='destructive' onClick={
              () => {
                mutateAsyncDeleteFlow({
                  idFlow: Number(selectedNode?.id),
                }).then(() => {
                  toast.success("Etapa excluída com sucesso");
                  queryClient.invalidateQueries(["processes"]);
                  queryClient.invalidateQueries(["services"]);
                  setMountTrigger(!mountTrigger);
                setBottomSheetOpen(false);
              });
            }}>
              Excluir
            </Button>
          </div>
        </SheetContent>
      </Sheet>
    </>
  );
}

ProcessPage.displayName = "ProcessPage";

export { ProcessPage };
