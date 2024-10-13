import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type DeleteUserRolesRequest = {
  token: string;
  userRole: {
    idUser: number;
    idRole: number;
  };
};

export type DeleteUserRolesResponse = void;

async function DeleteUserRoles({
  token,
  userRole,
}: DeleteUserRolesRequest): Promise<DeleteUserRolesResponse> {
  const response = await axiosClient.delete(
    `/user-roles/${userRole.idUser}/${userRole.idRole}`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function useDeleteUserRoles() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (userRole: DeleteUserRolesRequest["userRole"]) =>
      DeleteUserRoles({ token, userRole }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-users"] });
      queryClient.invalidateQueries({ queryKey: ["users"] });
      toast.success("Usuário removido com sucesso");
    },
  });
}

export { useDeleteUserRoles };
