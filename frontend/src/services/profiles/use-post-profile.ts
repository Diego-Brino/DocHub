import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostUserRequest = {
  name: string;
  email: string;
  username: string;
};

export type PostUserResponse = void;

async function postProfile(
  token: string,
  user: PostUserRequest,
): Promise<PostUserResponse> {
  const formData = new FormData();

  formData.append("name", user.name);
  formData.append("email", user.email);
  formData.append("username", user.username);
  formData.append("avatar", "");

  const response = await axiosClient.post(`/profiles/`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePostProfile() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: ({ user }: { user: PostUserRequest }) =>
      postProfile(token, user),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("Usuário criado com sucesso");
    },
  });
}

export { usePostProfile };
