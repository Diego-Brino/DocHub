import {useMutation} from "react-query";
import {toast} from "sonner";
import axiosClient from "@/lib/axios";

export type PostPasswordRecoveryChangeRequest = {
  token: string,
  newPassword: string
}

export type PostPasswordRecoveryChangeResponse = void

async function postPasswordRecoveryChange(data: PostPasswordRecoveryChangeRequest): Promise<PostPasswordRecoveryChangeResponse> {
  await axiosClient.post('/password-recovery/change', data);
}

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