import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog.tsx";
import { RecoverPasswordDialogForm } from "@/features/auth/recover-password-dialog/recover-password-dialog-form.tsx";
import { useRecoverPasswordDialogContext } from "./recover-password-dialog-context.tsx";

function RecoverPasswordDialog() {
  const { isOpen, close } = useRecoverPasswordDialogContext();

  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && close()}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Recuperar senha</DialogTitle>
          <DialogDescription>
            Insira seu e-mail para recuperar sua senha.
          </DialogDescription>
        </DialogHeader>
        <RecoverPasswordDialogForm />
      </DialogContent>
    </Dialog>
  );
}

RecoverPasswordDialog.displayName = "RecoverPasswordDialog";

export { RecoverPasswordDialog };
