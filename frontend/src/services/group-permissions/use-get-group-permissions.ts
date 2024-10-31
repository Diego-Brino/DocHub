import { useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type GetGroupPermissionsRequest = {
  token: string;
};

export type GetGroupPermissionsResponse = {
  id: number;
  description: string;
}[];

async function getGroupPermissions({
  token,
}: GetGroupPermissionsRequest): Promise<GetGroupPermissionsResponse> {
  const response = await axiosClient.get(`/group-permissions`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetGroupPermissions() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["group-permissions"],
    queryFn: () => getGroupPermissions({ token }),
  });
}

export { useGetGroupPermissions };
