import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { ArrowLeft, ArrowRight } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { Separator } from "@/components/ui/separator.tsx";
import { useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useEffect } from "react";
import { Card } from "@/components/ui/card.tsx";
import { Process, User } from "@/pages/flow.tsx";
import { Badge } from "@/components/ui/badge.tsx";

function GroupUseFlows() {
  const { token } = useAuthContext();

  const { id } = useParams();

  const { data: dataGroup } = useGetGroup(Number(id));

  const { data: dataMyRequests } = useQuery(
    ["requests"],
    async (): Promise<
      {
        id: number;
        user: User;
        process: Process;
        status: string;
        movements: any[];
        startDate: string;
      }[]
    > => {
      const response = await axiosClient.get(`users/requests`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
  );

  const navigate = useNavigate();

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
            buttons={<></>}
          />
          <div className="flex flex-col items-start justify-start w-full gap-4 overflow-y-scroll h-full">
            {dataMyRequests?.map((request) => (
              <Card
                key={request.id}
                className="w-full p-4 flex gap-4 items-center justify-between"
              >
                <div className="flex gap-2 items-center justify-start">
                  <h2 className="text-lg font-semibold">
                    {request.process.service.description}
                  </h2>
                  <Separator orientation="vertical" />
                  <p className="text-sm text-muted-foreground">
                    Iniciado em: {request.startDate.split(" ")[0]}
                  </p>
                </div>
                <div className="flex gap-4 items-center justify-start">
                  <Badge
                    variant={
                      request.status === "EM ANDAMENTO"
                        ? "default"
                        : "secondary"
                    }
                  >
                    {request.status}
                  </Badge>
                  <Button
                    className="gap-2 flex"
                    onClick={() => {
                      navigate(`/groups/${id}/use-flows/${request.id}`);
                    }}
                  >
                    Acessar
                    <ArrowRight />
                  </Button>
                </div>
              </Card>
            ))}
          </div>
        </GroupToolbarProvider>
      </div>
    </>
  );
}

GroupUseFlows.displayName = "GroupUseFlows";

export { GroupUseFlows };
