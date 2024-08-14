import {GroupsGridCard} from "./groups-grid-card.tsx";
import {useGetGroups} from "@/features/groups/hooks/use-get-groups.ts";
import {filterGroups} from "@/features/groups/utils";
import {useGroupsFilterContext} from "@/features/groups/hooks/use-groups-filter-context.ts";
import {GroupsGridCardSkeleton} from "@/features/groups/components/groups-grid-card-skeleton.tsx";
import {useMemo} from "react";

function GroupsGrid() {
  const {appliedFilter} = useGroupsFilterContext();
  const {data, isLoading} = useGetGroups();

  const filteredGroups = useMemo(() => filterGroups(appliedFilter, data || []), [appliedFilter, data]);
  
  return(
    <div className='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 h-full gap-4 p-4 overflow-scroll'>
      {!isLoading
        ? filteredGroups.length > 0 ?
          (
            <>
              {filteredGroups.map((group, index) => (
                <GroupsGridCard key={index} group={group}/>
              ))}
            </>
          ) : (
            <div className='col-span-full flex justify-center items-center'>
              <p className='text-muted-foreground'>Nenhum registro encontrado :(</p>
            </div>
          )
        : (
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