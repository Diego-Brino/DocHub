import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type PutUserRequest = {
  name: string;
  email: string;
  username: string;
};

export type PutUserResponse = {
  token: string;
};

async function putUser(
  token: string,
  id: number,
  user: PutUserRequest,
): Promise<PutUserResponse> {
  const formData = new FormData();

  formData.append("name", user.name);
  formData.append("email", user.email);
  formData.append("username", user.username);

  const response = await axiosClient.put(`/users/${id}`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePutUser() {
  const { token, tokenPayload, setToken } = useAuthContext();

  return useMutation({
    mutationKey: ["user"],
    mutationFn: (user: PutUserRequest) =>
      putUser(token, tokenPayload?.id as number, user),
    onSuccess: (res) => {
      setToken(res.token);
      toast.success("Perfil atualizado com sucesso");
    },
  });
}

export { usePutUser };
