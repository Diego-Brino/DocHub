import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type DeleteGroupRolePermissionsRequest = {
  token: string;
  groupRolePermission: {
    idGroupPermission: number;
    idRole: number;
    idGroup: number;
  };
};

export type DeleteGroupRolePermissionsResponse = void;

async function deleteGroupRolePermissions({
  token,
  groupRolePermission,
}: DeleteGroupRolePermissionsRequest): Promise<DeleteGroupRolePermissionsResponse> {
  const response = await axiosClient.delete(
    `/group-role-permissions/${groupRolePermission.idRole}/${groupRolePermission.idGroupPermission}/${groupRolePermission.idGroup}`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function useDeleteGroupRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      groupRolePermission: DeleteGroupRolePermissionsRequest["groupRolePermission"],
    ) => deleteGroupRolePermissions({ token, groupRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão removida com sucesso");
    },
  });
}

export { useDeleteGroupRolePermissions };
