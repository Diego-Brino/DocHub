import {ReactNode, useState} from "react";
import GroupsFilterContext from "@/features/groups/contexts/groups-filter-context.tsx";

type GroupsFilterProviderProps = {
  children: ReactNode
}

function GroupsFilterProvider({children}: GroupsFilterProviderProps){

  const [filter, setFilter] = useState<string>('');
  const [appliedFilter, setAppliedFilter] = useState<string>('');

  const applyFilter = () => {
    setAppliedFilter(filter);
  };

  return (
    <GroupsFilterContext.Provider value={{ filter, setFilter, appliedFilter, applyFilter }}>
      {children}
    </GroupsFilterContext.Provider>
  )
}

export {GroupsFilterProvider}