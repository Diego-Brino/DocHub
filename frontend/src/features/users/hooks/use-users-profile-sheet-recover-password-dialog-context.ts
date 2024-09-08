import {useContext} from "react";
import UsersProfileSheetAlterPasswordDialogContext
  from "@/features/users/contexts/users-profile-sheet-alter-password-dialog-context.tsx";

function useUsersProfileSheetAlterPasswordDialogContext(){
  const context = useContext(UsersProfileSheetAlterPasswordDialogContext)

  if (!context) {
    throw new Error("useUsersProfileSheetAlterPasswordDialogContext must be used within a UsersProfileSheetAlterPasswordDialogProfile");
  }

  return context;
}

export {useUsersProfileSheetAlterPasswordDialogContext}