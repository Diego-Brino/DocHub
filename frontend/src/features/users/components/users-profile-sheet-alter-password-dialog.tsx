import {Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle} from "@/components/ui/dialog.tsx";
import {
  useUsersProfileSheetAlterPasswordDialogContext
} from "@/features/users/hooks/use-users-profile-sheet-recover-password-dialog-context.ts";
import {
  UsersProfileSheetAlterPasswordDialogForm
} from "@/features/users/components/users-profile-sheet-alter-password-dialog-form.tsx";

function UsersProfileSheetAlterPasswordDialog() {
  const {isOpen, close} = useUsersProfileSheetAlterPasswordDialogContext();

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && close()}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            Alterar senha
          </DialogTitle>
          <DialogDescription>
            Insira sua nova senha.
          </DialogDescription>
        </DialogHeader>
        <UsersProfileSheetAlterPasswordDialogForm/>
      </DialogContent>
    </Dialog>
  )
}

UsersProfileSheetAlterPasswordDialog.displayName = "UsersProfileSheetAlterPasswordDialog"

export {UsersProfileSheetAlterPasswordDialog};