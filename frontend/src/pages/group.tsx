import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { GroupResources } from "@/features/groups/group-resources/group-resources.tsx";

function Group() {
  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
      <GroupToolbarProvider>
        <GroupToolbar />
        <GroupResources />
      </GroupToolbarProvider>
    </div>
  );
}

Group.displayName = "Group";

export { Group };
