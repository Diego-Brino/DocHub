import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type DeleteResourceRolePermissionsRequest = {
  token: string;
  resourceRolePermission: {
    idResourcePermission: number;
    idResource: number;
    idRole: number;
  };
};

export type DeleteResourceRolePermissionsResponse = void;

async function deleteResourceRolePermissions({
  token,
  resourceRolePermission,
}: DeleteResourceRolePermissionsRequest): Promise<DeleteResourceRolePermissionsResponse> {
  const response = await axiosClient.delete(
    `/resource-role-permissions/${resourceRolePermission.idRole}/${resourceRolePermission.idResourcePermission}/${resourceRolePermission.idResource}`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function useDeleteResourceRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      resourceRolePermission: DeleteResourceRolePermissionsRequest["resourceRolePermission"],
    ) => deleteResourceRolePermissions({ token, resourceRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão removida com sucesso");
    },
  });
}

export { useDeleteResourceRolePermissions };
