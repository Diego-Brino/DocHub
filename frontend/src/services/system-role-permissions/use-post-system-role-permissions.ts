import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostSystemRolePermissionsRequest = {
  token: string;
  systemRolePermission: {
    idSystemPermission: number;
    idRole: number;
  };
};

export type PostSystemRolePermissionsResponse = void;

async function postSystemRolePermissions({
  token,
  systemRolePermission,
}: PostSystemRolePermissionsRequest): Promise<PostSystemRolePermissionsResponse> {
  const response = await axiosClient.post(
    `/system-role-permissions`,
    systemRolePermission,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function usePostSystemRolePermissions() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (
      systemRolePermission: PostSystemRolePermissionsRequest["systemRolePermission"],
    ) => postSystemRolePermissions({ token, systemRolePermission }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["role-permissions"] });
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Permissão adicionada com sucesso");
    },
  });
}

export { usePostSystemRolePermissions };
