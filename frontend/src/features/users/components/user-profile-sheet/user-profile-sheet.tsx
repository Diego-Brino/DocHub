import {Sheet, SheetContent, SheetDescription, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";

import {UserProfileSheetForm} from "@/features/users/components/user-profile-sheet/user-profile-sheet-form.tsx";
import {UserProfileSheetAvatar} from "@/features/users/components/user-profile-sheet/user-profile-sheet-avatar.tsx";
import {
  AlterPasswordDialog
} from "@/features/users/components/alter-password-dialog/alter-password-dialog.tsx";
import {
  useUserProfileSheetContext
} from "@/features/users/components/user-profile-sheet/user-profile-sheet-context.tsx";
import {AlterPasswordDialogProvider} from "@/features/users/components/alter-password-dialog";

function UserProfileSheet() {
  const {isOpen, close} = useUserProfileSheetContext()

  return (
    <AlterPasswordDialogProvider>
      <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
        <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
          <SheetHeader>
            <SheetTitle>Perfil</SheetTitle>
            <SheetDescription>
              Edite suas informações relacionadas a sua conta.
            </SheetDescription>
          </SheetHeader>
          <UserProfileSheetAvatar/>
          <UserProfileSheetForm/>
        </SheetContent>
      </Sheet>
      <AlterPasswordDialog/>
    </AlterPasswordDialogProvider>
  )
}

UserProfileSheet.displayName = 'UserProfileSheet'

export {UserProfileSheet}