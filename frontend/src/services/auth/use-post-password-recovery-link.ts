import {useMutation} from "react-query";
import {toast} from "sonner";
import axiosClient from "@/lib/axios";

export type PostPasswordRecoveryLinkRequest = {
  email: string
}

export type PostPasswordRecoveryLinkResponse = void

async function postPasswordRecoveryLink(data: PostPasswordRecoveryLinkRequest): Promise<PostPasswordRecoveryLinkResponse> {
  await axiosClient.post(`/password-recovery/link?email=${data.email}`);
}

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