import {createContext} from "react";

type AuthContext = {
  token: string
  setToken: (value: string) => void,
}

const GroupsFilterContext = createContext<AuthContext>({
  token: '',
  setToken: () => {}
});

export default GroupsFilterContext;