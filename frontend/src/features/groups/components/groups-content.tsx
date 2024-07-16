import {GroupsToolbar} from "@/features/groups/components/groups-toolbar.tsx";
import {GroupsGrid} from "@/features/groups/components/groups-grid.tsx";
import {GroupsFilterProvider} from "@/features/groups/providers/groups-filter-provider.tsx";

function GroupsContent() {

  return (
    <div className='flex flex-col w-full overflow-hidden bg-muted/60 md:border rounded h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-4rem)]'>
      <GroupsFilterProvider>
        <GroupsToolbar/>
        <GroupsGrid/>
      </GroupsFilterProvider>
    </div>
  )
}

GroupsContent.displayName = 'GroupsContent'

export {GroupsContent}