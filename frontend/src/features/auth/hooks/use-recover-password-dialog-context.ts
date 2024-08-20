import {useContext} from "react";
import RecoverPasswordDialogContext from "@/features/auth/contexts/recover-password-dialog-context.tsx";

function useRecoverPasswordDialogContext(){
  const context = useContext(RecoverPasswordDialogContext)

  if (!context) {
    throw new Error("useRecoverPasswordDialogContext must be used within a RecoverPasswordDialogProvider");
  }

  return context;
}

export {useRecoverPasswordDialogContext}