import {useContext} from "react";
import GroupsFilterContext from "@/features/groups/contexts/groups-filter-context.tsx";

function useGroupsFilterContext(){
  const context = useContext(GroupsFilterContext)

  if (!context) {
    throw new Error("useGroupsFilterContext must be used within a GroupsFilterProvider");
  }

  return context;
}

export {useGroupsFilterContext}