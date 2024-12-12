import { useNavigate, useParams } from "react-router-dom";
import { useAuthContext } from "@/contexts/auth";
import { Button } from "@/components/custom/button.tsx";
import { ArrowLeft, PackagePlus } from "lucide-react";
import { Separator } from "@/components/ui/separator.tsx";
import {
  GroupToolbar,
  GroupToolbarProvider,
  useGroupToolbarContext,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { useMutation, useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { ProcessCard } from "@/features/flows/service-card/process-card.tsx";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";

export type Process = {
  id: number;
  startDate: string;
  endDate: string;
  group: Group;
  service: ProcessService;
  flows: Flow[];
};

type FlowProcess = {
  id: number;
  startDate: string;
  endDate: string;
  group: Group;
  service: ProcessService;
};

type Flow = {
  id: number;
  order: number;
  time: number;
  limitDate: string;
  activity: Activity;
  process: FlowProcess;
  responseFlows: ResponseFlow[];
  flowUsers: FlowUser[];
};

type FlowUser = {
  user: User;
  flow: Flow;
};

export type User = {
  id: number;
  name: string;
  email: string;
  username: string;
  avatarUrl: string;
  lastAccess: string;
};

export type ResponseFlow = {
  flow: ResponseFlowFlow;
  response: Response;
  destinationFlow: DestinationFlow;
};

type DestinationFlow = {
  id: number;
  order: number;
  time: number;
  limitDate: string;
  process: FlowProcess;
  activity: Activity;
};

type Response = {
  id: number;
  description: string;
};

type ResponseFlowFlow = {
  id: number;
  order: number;
  time: number;
  limitDate: string;
  activity: Activity;
  process: FlowProcess;
};

type ProcessService = {
  id: number;
  description: string;
};

type Group = {
  id: number;
  idS3Bucket: string;
  name: string;
  description: string;
  groupUrl: string;
};

type Activity = {
  id: number;
  description: string;
};

function Flow() {
  const { token } = useAuthContext();

  const { id, flowId } = useParams();

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

  const { data: dataServiceProcesses } = useQuery(
    ["services", flowId, "processes"],
    async (): Promise<Process[]> => {
      const response = await axiosClient.get(`/services/${flowId}/processes`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const { mutateAsync: mutateAsyncPostProcess, isLoading } = useMutation({
    mutationFn: () =>
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
      toast.success("Processo criado com sucesso");
    },
  });

  const {
    mutateAsync: mutateAsyncPatchProcessInProgress,
    isLoading: isLoadingPatchProcessInProgress,
  } = useMutation({
    mutationFn: () =>
      axiosClient.patch(
        `/processes/${flowId}`,
        {},
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

  const submitFormProcess = () => {
    mutateAsyncPostProcess();
  };

  const ProcessList = () => {
    const { appliedFilter } = useGroupToolbarContext();

    return (
      <div className="flex flex-col items-start justify-start w-full pb-4 gap-4 overflow-scroll h-full">
        {dataServiceProcesses
          ?.filter((process, index) =>
            ("Processo" + " - " + index)
              .toLowerCase()
              .includes(appliedFilter.toLowerCase()),
          )
          .map((process, index) => (
            <ProcessCard process={process} order={index + 1} key={process.id} />
          ))}
      </div>
    );
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
            <h2 className="text-lg font-semibold">
              {dataService?.description}
            </h2>
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
                    submitFormProcess();
                  }}
                >
                  <PackagePlus />
                  Criar Novo Processo
                </Button>
              </>
            }
          />
          <ProcessList />
        </GroupToolbarProvider>
      </div>
    </>
  );
}

Flow.displayName = "Flow";

export { Flow };
