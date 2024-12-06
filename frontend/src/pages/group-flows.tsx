import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { ArrowLeft } from "lucide-react";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { Separator } from "@/components/ui/separator.tsx";

function GroupFlows() {
  const { id } = useParams();

  const { data: dataGroup } = useGetGroup(Number(id));

  const navigate = useNavigate();

  return (
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
          showFolderButtons={false}
        />
        <div className="flex flex-col items-end justify-center w-full pb-4 gap-2"></div>
      </GroupToolbarProvider>
    </div>
  );
}

GroupFlows.displayName = "GroupFlows";

export { GroupFlows };
