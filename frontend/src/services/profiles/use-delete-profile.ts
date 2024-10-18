import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type DeleteProfileRequest = {
  token: string;
  profileId: number;
};

export type DeleteProfileResponse = void;

async function deleteProfile({
  token,
  profileId,
}: DeleteProfileRequest): Promise<DeleteProfileResponse> {
  const response = await axiosClient.delete(`/profiles/${profileId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useDeleteProfile({ profileId }: Omit<DeleteProfileRequest, "token">) {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: () => deleteProfile({ token, profileId }),
    onSuccess: () => {
      queryClient.invalidateQueries(["users"]);
      toast.success("Usuário excluído com sucesso");
    },
  });
}

export { useDeleteProfile };
