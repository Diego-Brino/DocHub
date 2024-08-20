import {Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle} from "@/components/ui/dialog.tsx";
import {useRecoverPasswordDialogContext} from "@/features/auth/hooks/use-recover-password-dialog-context.ts";

function RecoverPasswordDialog() {
  const {isOpen, close} = useRecoverPasswordDialogContext();

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && close()}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>
            Recuperar senha
          </DialogTitle>
          <DialogDescription>
            Insira seu e-mail para recuperar sua senha.
          </DialogDescription>
        </DialogHeader>

      </DialogContent>
    </Dialog>
  )
}

RecoverPasswordDialog.displayName = "RecoverPasswordDialog"

export {RecoverPasswordDialog};
