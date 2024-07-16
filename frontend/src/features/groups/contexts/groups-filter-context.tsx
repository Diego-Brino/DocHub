import {createContext} from "react";

type GroupsFilterContext = {
  filter: string
  setFilter: (value: string) => void
}

const GroupsFilterContext = createContext<GroupsFilterContext>({
  filter: '',
  setFilter: () => {}
});

export default GroupsFilterContext;