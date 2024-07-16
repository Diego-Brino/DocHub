import {ReactNode, useState} from "react";
import GroupsFilterContext from "@/features/groups/contexts/groups-filter-context.tsx";

type GroupsFilterProviderProps = {
  children: ReactNode
}

function GroupsFilterProvider({children}: GroupsFilterProviderProps){

  const [filter, setFilter] = useState<string>('');

  return (
    <GroupsFilterContext.Provider value={{ filter, setFilter }}>
      {children}
    </GroupsFilterContext.Provider>
  )
}

export {GroupsFilterProvider}