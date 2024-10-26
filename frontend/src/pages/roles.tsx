import { RolesList } from "@/features/roles/roles-list/roles-list.tsx";
import { RolesToolbar } from "@/features/roles/roles-toolbar";
import { RolesToolbarProvider } from "@/features/roles/roles-toolbar/roles-toolbar.tsx";
import {
  RoleSheet,
  RoleSheetProvider,
} from "@/features/roles/role-sheet/role-sheet.tsx";
import {
  RoleUsersDialog,
  RoleUsersDialogProvider,
} from "@/features/roles/role-users-dialog/role-users-dialog.tsx";
import {
  RolePermissionsDialog,
  RolePermissionsDialogProvider,
} from "@/features/roles/role-permissions-dialog/role-permissions-dialog.tsx";
import {
  RolePermissionDeleteConfirmationAlert,
  RolePermissionDeleteConfirmationAlertProvider,
} from "@/features/roles/role-permission-delete-confirmation-alert/role-permission-delete-confirmation-alert.tsx";

function Roles() {
  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
      <RoleSheetProvider>
        <RoleUsersDialogProvider>
          <RolePermissionsDialogProvider>
            <RolesToolbarProvider>
              <RolesToolbar />
              <RolesList />
            </RolesToolbarProvider>
            <RoleSheet />
            <RoleUsersDialog />
            <RolePermissionDeleteConfirmationAlertProvider>
              <RolePermissionsDialog />
              <RolePermissionDeleteConfirmationAlert />
            </RolePermissionDeleteConfirmationAlertProvider>
          </RolePermissionsDialogProvider>
        </RoleUsersDialogProvider>
      </RoleSheetProvider>
    </div>
  );
}

Roles.displayName = "Roles";

export { Roles };
