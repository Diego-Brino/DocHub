import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostUserRolesRequest = {
  token: string;
  userRole: {
    idUser: number;
    idRole: number;
  };
};

export type PostUserRolesResponse = void;

async function postUserRoles({
  token,
  userRole,
}: PostUserRolesRequest): Promise<PostUserRolesResponse> {
  const response = await axiosClient.post(`/user-roles`, userRole, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePostUserRoles() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (userRole: PostUserRolesRequest["userRole"]) =>
      postUserRoles({ token, userRole }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-users"] });
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("Usuário adicionado com sucesso");
    },
  });
}

export { usePostUserRoles };
