import {Sheet, SheetContent, SheetDescription, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {useUsersProfileSheetContext} from "@/features/users/hooks/use-users-profile-sheet-context.ts";
import {UsersProfileSheetForm} from "@/features/users/components/users-profile-sheet-form.tsx";
import {UsersProfileSheetAvatar} from "@/features/users/components/users-profile-sheet-avatar.tsx";
import {
  UsersProfileSheetAlterPasswordDialogProvider
} from "@/features/users/providers/users-profile-sheet-recover-password-dialog-provider.tsx";
import {
  UsersProfileSheetAlterPasswordDialog
} from "@/features/users/components/users-profile-sheet-alter-password-dialog.tsx";

function UsersProfileSheet() {
  const {isOpen, close} = useUsersProfileSheetContext()

  return (
    <UsersProfileSheetAlterPasswordDialogProvider>
      <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
        <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
          <SheetHeader>
            <SheetTitle>Perfil</SheetTitle>
            <SheetDescription>
              Edite suas informações relacionadas a sua conta.
            </SheetDescription>
          </SheetHeader>
          <UsersProfileSheetAvatar/>
          <UsersProfileSheetForm/>
        </SheetContent>
      </Sheet>
      <UsersProfileSheetAlterPasswordDialog/>
    </UsersProfileSheetAlterPasswordDialogProvider>
  )
}

UsersProfileSheet.displayName = 'UsersProfileSheet'

export {UsersProfileSheet}