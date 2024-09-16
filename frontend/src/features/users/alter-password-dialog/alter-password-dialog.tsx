import {Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle} from "@/components/ui/dialog.tsx";
import {
  AlterPasswordDialogForm
} from "@/features/users/alter-password-dialog/alter-password-dialog-form.tsx";
import {
  useAlterPasswordDialogContext
} from "@/features/users/alter-password-dialog/alter-password-dialog-context.tsx";

function AlterPasswordDialog() {
  const {isOpen, close} = useAlterPasswordDialogContext();

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
        <AlterPasswordDialogForm/>
      </DialogContent>
    </Dialog>
  )
}

AlterPasswordDialog.displayName = "AlterPasswordDialog"

export {AlterPasswordDialog};