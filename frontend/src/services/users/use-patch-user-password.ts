import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type PatchUserPasswordRequest = {
  oldPassword: string;
  newPassword: string;
};

export type PatchUserPasswordResponse = {
  token: string;
};

async function patchUserPassword(
  token: string,
  id: number,
  data: PatchUserPasswordRequest,
): Promise<PatchUserPasswordResponse> {
  const response = await axiosClient.patch(`/users/${id}/password`, data, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePatchUserPassword() {
  const { token, tokenPayload, setToken } = useAuthContext();

  return useMutation({
    mutationKey: ["user", token, "password"],
    mutationFn: (data: PatchUserPasswordRequest) =>
      patchUserPassword(token, tokenPayload?.id as number, data),
    onSuccess: (res) => {
      setToken(res.token);
      toast.success("Senha alterada com sucesso");
    },
  });
}

export { usePatchUserPassword };
