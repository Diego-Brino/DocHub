import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetRoleRequest = {
  id: number | null;
  token: string;
};

export type GetRoleResponse = {
  id: number;
  name: string;
  description: string;
  color: string;
  status: string;
  systemPermissions: { id: number; description: string }[];
};

async function getRole({
  token,
  id,
}: GetRoleRequest): Promise<GetRoleResponse> {
  const response = await axiosClient.get(`/roles/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetRole({ id }: Pick<GetRoleRequest, "id">) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["roles", id],
    queryFn: () => getRole({ token, id }),
    enabled: !!id,
  });
}

export { useGetRole };
