import { useMutation } from "react-query";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

async function patchUserAvatar(
  token: string,
  id: number,
  avatar: File,
): Promise<void> {
  const formData = new FormData();
  formData.append("avatar", avatar);

  await axiosClient.patch(`/users/${id}/avatar`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "multipart/form-data",
    },
  });
}

function usePatchUserAvatar() {
  const { token, tokenPayload } = useAuthContext();

  return useMutation({
    mutationKey: ["user", "avatar"],
    mutationFn: (avatar: File) =>
      patchUserAvatar(token, tokenPayload?.id as number, avatar),
    onSuccess: () => {
      queryClient.invalidateQueries(["user", "roles"]);
      toast.success("Avatar atualizado com sucesso");
    },
  });
}

export { usePatchUserAvatar };
