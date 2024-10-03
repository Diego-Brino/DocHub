import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetRolesRequest = {
  token: string;
};

export type GetRolesResponse = {
  id: number;
  name: string;
  description: string;
  color: string;
  status: string;
  systemPermissions: { id: number; description: string }[];
}[];

async function getRoles({ token }: GetRolesRequest): Promise<GetRolesResponse> {
  const response = await axiosClient.get("/roles", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetRoles() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["roles", token],
    queryFn: () => getRoles({ token }),
  });
}

export { useGetRoles };
