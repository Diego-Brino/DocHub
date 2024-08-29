import {createContext} from "react";

type UsersProfileSheetContext = {
  isOpen: boolean,
  open: () => void,
  close: () => void
}

const UsersProfileSheetContext = createContext<UsersProfileSheetContext>({
  isOpen: false,
  open: () => {},
  close: () => {}
});

export default UsersProfileSheetContext;