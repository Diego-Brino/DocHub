import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type DeleteSystemRolePermissionsRequest = {
  token: string;
  systemRolePermission: {
    idSystemPermission: number;
    idRole: number;
  };
};

export type DeleteSystemRolePermissionsResponse = void;

async function deleteSystemRolePermissions({
  token,
  systemRolePermission,
}: DeleteSystemRolePermissionsRequest): Promise<DeleteSystemRolePermissionsResponse> {
  const response = await axiosClient.delete(
    `/system-role-permissions/${systemRolePermission.idRole}/${systemRolePermission.idSystemPermission}`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function useDeleteSystemRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      systemRolePermission: DeleteSystemRolePermissionsRequest["systemRolePermission"],
    ) => deleteSystemRolePermissions({ token, systemRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão removida com sucesso");
    },
  });
}

export { useDeleteSystemRolePermissions };
