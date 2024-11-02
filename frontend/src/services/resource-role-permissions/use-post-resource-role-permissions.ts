import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostResourceRolePermissionsRequest = {
  token: string;
  resourceRolePermission: {
    idResourcePermission: number;
    idResource: number;
    idRole: number;
  };
};

export type PostResourceRolePermissionsResponse = void;

async function postResourceRolePermissions({
  token,
  resourceRolePermission,
}: PostResourceRolePermissionsRequest): Promise<PostResourceRolePermissionsResponse> {
  const response = await axiosClient.post(
    `/resource-role-permissions`,
    resourceRolePermission,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function usePostResourceRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      resourceRolePermission: PostResourceRolePermissionsRequest["resourceRolePermission"],
    ) => postResourceRolePermissions({ token, resourceRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão adicionada com sucesso");
    },
  });
}

export { usePostResourceRolePermissions };
