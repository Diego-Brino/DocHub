import { UsersToolbar } from "@/features/users/users-toolbar";
import { UsersList } from "@/features/users/users-list/users-list.tsx";
import { UsersToolbarProvider } from "@/features/users/users-toolbar/users-toolbar.tsx";
import {
  UserSheet,
  UserSheetProvider,
} from "@/features/users/user-sheet/user-sheet.tsx";

function Users() {
  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-4rem)]">
      <UserSheetProvider>
        <UsersToolbarProvider>
          <UsersToolbar />
          <UsersList />
        </UsersToolbarProvider>
        <UserSheet />
      </UserSheetProvider>
    </div>
  );
}

Users.displayName = "Users";

export { Users };
