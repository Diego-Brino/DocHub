import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PutRoleRequest = {
  token: string;
  role: {
    id: number;
    name: string;
    description: string;
    color: string;
    roleStatus: "ACTIVE" | "INACTIVE";
  };
};

export type PutRoleResponse = void;

async function putRole({
  token,
  role,
}: PutRoleRequest): Promise<PutRoleResponse> {
  const response = await axiosClient.put(`/roles/${role.id}`, role, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePutRole() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (role: PutRoleRequest["role"]) => putRole({ token, role }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Cargo atualizado com sucesso");
    },
  });
}

export { usePutRole };
