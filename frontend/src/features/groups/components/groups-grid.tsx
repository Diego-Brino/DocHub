import {GroupsGridCard} from "./groups-grid-card.tsx";
import {useGetGroups} from "@/features/groups/hooks/use-get-groups.ts";
import {filterGroups} from "@/features/groups/utils";
import {useGroupsFilterContext} from "@/features/groups/hooks/use-groups-filter-context.ts";
import {GroupsGridCardSkeleton} from "@/features/groups/components/groups-grid-card-skeleton.tsx";

function GroupsGrid() {
  const {filter} = useGroupsFilterContext();
  const {data, isLoading} = useGetGroups();

  return(
    <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 p-4 overflow-scroll'>
      {!isLoading
        ? (
          <>
            {filterGroups(filter, data || []).map((group, index) => (
              <GroupsGridCard key={index} group={group}/>
            ))}
          </>
        ) : (
          <>
            {[1, 2, 3, 4, 5, 6].map((_group, index) => (
              <GroupsGridCardSkeleton key={index}/>
            ))}
          </>
        )
      }
    </div>
  )
}

GroupsGrid.displayName = 'GroupsGrid'

export {GroupsGrid}