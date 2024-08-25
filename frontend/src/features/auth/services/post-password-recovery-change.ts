import axiosClient from "@/lib/axios";

type PostPasswordRecoveryChangeRequest = {
  token: string,
  newPassword: string
}

type PostPasswordRecoveryChangeResponse = void

async function postPasswordRecoveryChange(data: PostPasswordRecoveryChangeRequest): Promise<PostPasswordRecoveryChangeResponse> {
  await axiosClient.post('/password-recovery/change', data);
}

export default postPasswordRecoveryChange;