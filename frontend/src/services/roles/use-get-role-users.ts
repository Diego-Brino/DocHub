import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetRoleUsersRequest = {
  roleId: number;
  token: string;
};

export type GetRoleUsersResponse = {
  id: number;
  name: string;
  email: string;
  username: string;
  avatarUrl: string;
}[];

async function getRoleUsers({
  token,
  roleId,
}: GetRoleUsersRequest): Promise<GetRoleUsersResponse> {
  const response = await axiosClient.get(`/roles/${roleId}/users`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetRoleUsers({ roleId }: Omit<GetRoleUsersRequest, "token">) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["roles-users", roleId],
    queryFn: () => getRoleUsers({ token, roleId }),
  });
}

export { useGetRoleUsers };
