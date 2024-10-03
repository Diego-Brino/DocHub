import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type PatchRoleRequest = {
  token: string;
  roleId: number;
  roleStatus: "ACTIVE" | "INACTIVE";
};

export type PatchRoleResponse = void;

async function patchRoleStatus({
  token,
  roleId,
  roleStatus,
}: PatchRoleRequest): Promise<PatchRoleResponse> {
  const response = await axiosClient.patch(
    `/roles/${roleId}/status?roleStatus=${roleStatus}`,
    {},
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );
  return response.data;
}

function usePatchRoleStatus({
  roleId,
}: Omit<PatchRoleRequest, "token" | "roleStatus">) {
  const { token } = useAuthContext();

  return useMutation({
    mutationKey: ["roles", roleId, "status"],
    mutationFn: ({ roleStatus }: Pick<PatchRoleRequest, "roleStatus">) =>
      patchRoleStatus({ token, roleId, roleStatus }),
    onSuccess: () => {
      toast.success("Status do cargo alterado com sucesso");
      queryClient.invalidateQueries(["roles"]);
    },
  });
}

export { usePatchRoleStatus };
