import {useMutation} from "react-query";
import postPasswordRecoveryLink from "@/features/auth/services/post-password-recovery-link.ts";
import {toast} from "sonner";

function usePostPasswordRecoveryLink(){
  return useMutation({
    mutationKey: ['password-recovery', 'link'],
    mutationFn: postPasswordRecoveryLink,
    onSuccess: () => {
      toast.success("Um email de recuperação de senha foi enviado")
    },
  });
}

export {usePostPasswordRecoveryLink}