import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostSystemRolePermissionsRequest = {
  token: string;
  groupRolePermission: {
    idGroupPermission: number;
    idRole: number;
    idGroup: number;
  };
};

export type PostSystemRolePermissionsResponse = void;

async function postGroupRolePermissions({
  token,
  groupRolePermission,
}: PostSystemRolePermissionsRequest): Promise<PostSystemRolePermissionsResponse> {
  const response = await axiosClient.post(
    `/group-role-permissions`,
    groupRolePermission,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function usePostGroupRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      groupRolePermission: PostSystemRolePermissionsRequest["groupRolePermission"],
    ) => postGroupRolePermissions({ token, groupRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão adicionada com sucesso");
    },
  });
}

export { usePostGroupRolePermissions };
