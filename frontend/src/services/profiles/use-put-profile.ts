import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PutUserRequest = {
  name: string;
  email: string;
  username: string;
};

export type PutUserResponse = void;

async function putProfile(
  token: string,
  id: number,
  user: PutUserRequest,
): Promise<PutUserResponse> {
  const formData = new FormData();

  formData.append("name", user.name);
  formData.append("email", user.email);
  formData.append("username", user.username);

  const response = await axiosClient.put(`/profiles/${id}`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePutProfile() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: ({ user, id }: { user: PutUserRequest; id: number }) =>
      putProfile(token, id, user),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("Usuário atualizado com sucesso");
    },
  });
}

export { usePutProfile };
