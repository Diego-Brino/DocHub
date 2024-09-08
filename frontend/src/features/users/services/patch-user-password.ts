import axiosClient from "@/lib/axios";

export type PatchUserPasswordRequest = {
  oldPassword: string,
  newPassword: string,
}

export type PatchUserPasswordResponse = {
  token: string,
}

async function patchUserPassword(token: string, id: number, data: PatchUserPasswordRequest): Promise<PatchUserPasswordResponse> {
  const response = await axiosClient.patch(`/users/${id}/password`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

export default patchUserPassword;