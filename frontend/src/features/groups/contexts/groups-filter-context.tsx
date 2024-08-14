import {createContext} from "react";

type GroupsFilterContext = {
  filter: string
  setFilter: (value: string) => void,
  appliedFilter: string
  applyFilter: () => void,
}

const GroupsFilterContext = createContext<GroupsFilterContext>({
  filter: '',
  setFilter: () => {},
  appliedFilter: '',
  applyFilter: () => {},
});

export default GroupsFilterContext;