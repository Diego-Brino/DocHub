import {useMutation} from "react-query";
import {toast} from "sonner";
import postPasswordRecoveryChange from "@/features/auth/services/post-password-recovery-change.ts";

function usePostPasswordRecoveryChange(){
  return useMutation({
    mutationKey: ['password-recovery', 'change'],
    mutationFn: postPasswordRecoveryChange,
    onSuccess: () => {
      toast.success("Senha alterada com sucesso");
    },
  });
}

export {usePostPasswordRecoveryChange}