import {
  GetGroupsResponse,
  useGetGroups,
} from "@/services/groups/use-get-groups.ts";
import { AnimatePresence } from "framer-motion";
import { useGroupsToolbarContext } from "@/features/groups/groups-toolbar/groups-toolbar.tsx";
import { GroupDeleteConfirmationAlertProvider } from "@/features/groups/group-delete-confirmation-alert/group-delete-confirmation-alert.tsx";
import { GroupCard } from "@/features/groups/group-card/group-card.tsx";
import { GroupCardSkeleton } from "@/features/groups/group-card-skeleton/group-card-skeleton.tsx";

const filterGroups = (filter: string, groups: GetGroupsResponse) => {
  return groups.filter((group) =>
    group.name.toLowerCase().includes(filter.toLowerCase()),
  );
};

function GroupsList() {
  const { data, isLoading } = useGetGroups();
  const { appliedFilter } = useGroupsToolbarContext();

  let filteredGroups = filterGroups(appliedFilter, data || []);

  filteredGroups = [...filteredGroups, ...filteredGroups, ...filteredGroups];

  return (
    <GroupDeleteConfirmationAlertProvider>
      <div className="w-full h-full gap-4 grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 overflow-y-scroll content-start relative mb-8 md:mb-0">
        {!isLoading && data ? (
          filteredGroups.length > 0 ? (
            <AnimatePresence>
              {filteredGroups.map((group) => (
                <GroupCard key={group.id} group={group} />
              ))}
            </AnimatePresence>
          ) : (
            <div className="absolute flex justify-center items-center top-1/2 right-1/2 translate-x-1/2 -translate-y-1/2">
              <p className="text-muted-foreground">
                Nenhum registro encontrado.
              </p>
            </div>
          )
        ) : (
          <>
            {[1, 2, 3, 4, 5, 6].map((_group, index) => (
              <GroupCardSkeleton key={index} />
            ))}
          </>
        )}
      </div>
    </GroupDeleteConfirmationAlertProvider>
  );
}

export { GroupsList };
