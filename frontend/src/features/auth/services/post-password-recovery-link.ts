import axiosClient from "@/lib/axios";

type PostPasswordRecoveryLinkRequest = {
  email: string
}

type PostPasswordRecoveryLinkResponse = void

async function postPasswordRecoveryLink(data: PostPasswordRecoveryLinkRequest): Promise<PostPasswordRecoveryLinkResponse> {
  await axiosClient.post(`/password-recovery/link?email=${data.email}`);
}

export default postPasswordRecoveryLink;