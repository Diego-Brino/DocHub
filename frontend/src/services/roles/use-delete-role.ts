import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type DeleteRoleRequest = {
  token: string;
  roleId: number;
};

export type DeleteRoleResponse = void;

async function deleteRole({
  token,
  roleId,
}: DeleteRoleRequest): Promise<DeleteRoleResponse> {
  const response = await axiosClient.delete(`/roles/${roleId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useDeleteRole({ roleId }: Omit<DeleteRoleRequest, "token">) {
  const { token } = useAuthContext();

  return useMutation({
    mutationKey: ["roles", roleId],
    mutationFn: () => deleteRole({ token, roleId }),
    onSuccess: () => {
      queryClient.invalidateQueries(["roles"]);
      toast.success("Cargo excluído com sucesso");
    },
  });
}

export { useDeleteRole };
