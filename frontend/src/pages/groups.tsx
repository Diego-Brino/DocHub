import {
  GroupSheet,
  GroupSheetProvider,
} from "@/features/groups/group-sheet/group-sheet.tsx";
import {
  GroupsToolbar,
  GroupsToolbarProvider,
} from "@/features/groups/groups-toolbar/groups-toolbar.tsx";
import { GroupsList } from "@/features/groups/groups-list/groups-list.tsx";

function Groups() {
  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-4rem)]">
      <GroupSheetProvider>
        <GroupsToolbarProvider>
          <GroupsToolbar />
          <GroupsList />
        </GroupsToolbarProvider>
        <GroupSheet />
      </GroupSheetProvider>
    </div>
  );
}

Groups.displayName = "Groups";

export { Groups };
