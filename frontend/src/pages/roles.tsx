import { RolesList } from "@/features/roles/roles-list/roles-list.tsx";
import { RolesToolbar } from "@/features/roles/roles-toolbar";
import { RolesToolbarProvider } from "@/features/roles/roles-toolbar/roles-toolbar.tsx";
import {
  RoleSheet,
  RoleSheetProvider,
} from "@/features/roles/role-sheet/role-sheet.tsx";

function Roles() {
  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-4rem)]">
      <RoleSheetProvider>
        <RolesToolbarProvider>
          <RolesToolbar />
          <RolesList />
        </RolesToolbarProvider>
        <RoleSheet />
      </RoleSheetProvider>
    </div>
  );
}

Roles.displayName = "Roles";

export { Roles };
