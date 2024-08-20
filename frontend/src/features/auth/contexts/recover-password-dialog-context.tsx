import {createContext} from "react";

type RecoverPasswordDialogContext = {
  isOpen: boolean,
  open: () => void,
  close: () => void
}

const RecoverPasswordDialogContext = createContext<RecoverPasswordDialogContext>({
  isOpen: false,
  open: () => {},
  close: () => {}
});

export default RecoverPasswordDialogContext;