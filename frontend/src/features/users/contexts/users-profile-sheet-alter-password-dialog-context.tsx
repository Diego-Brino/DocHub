import {createContext} from "react";

type UsersProfileSheetAlterPasswordDialogContext = {
  isOpen: boolean,
  open: () => void,
  close: () => void
}

const UsersProfileSheetAlterPasswordDialogContext = createContext<UsersProfileSheetAlterPasswordDialogContext>({
  isOpen: false,
  open: () => {},
  close: () => {}
});

export default UsersProfileSheetAlterPasswordDialogContext;