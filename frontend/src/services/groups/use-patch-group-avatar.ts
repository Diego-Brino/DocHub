import { useMutation } from "react-query";
import queryClient from "@/lib/react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

async function patchGroupAvatar(
  token: string,
  id: number,
  avatar: File,
): Promise<void> {
  const formData = new FormData();
  formData.append("avatar", avatar);

  await axiosClient.patch(`/groups/${id}/avatar`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "multipart/form-data",
    },
  });
}

function usePatchGroupAvatar() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: ({ avatar, id }: { avatar: File; id: number }) =>
      patchGroupAvatar(token, id, avatar),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      toast.success("Avatar atualizado com sucesso");
    },
  });
}

export { usePatchGroupAvatar };
