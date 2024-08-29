import {useContext} from "react";
import UsersProfileSheetContext from "@/features/users/contexts/users-profile-sheet-context.tsx";

function useUsersProfileSheetContext(){
  const context = useContext(UsersProfileSheetContext);

  if (!context) {
    throw new Error("useUsersProfileSheetContext must be used within a UsersProfileSheetProvider");
  }

  return context;
}

export {useUsersProfileSheetContext}