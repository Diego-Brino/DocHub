import { useMutation } from "react-query";
import { toast } from "sonner";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import queryClient from "@/lib/react-query";

export type PostRoleRequest = {
  token: string;
  role: {
    name: string;
    description: string;
    color: string;
  };
};

export type PostRoleResponse = void;

async function postRole({
  token,
  role,
}: PostRoleRequest): Promise<PostRoleResponse> {
  const response = await axiosClient.post(`/roles`, role, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function usePostRole() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (role: PostRoleRequest["role"]) => postRole({ token, role }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["roles"] });
      toast.success("Cargo criado com sucesso");
    },
  });
}

export { usePostRole };
