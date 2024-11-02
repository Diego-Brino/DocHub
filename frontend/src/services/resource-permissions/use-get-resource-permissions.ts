import { useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type GetResourcePermissionsRequest = {
  token: string;
};

export type GetResourcePermissionsResponse = {
  id: number;
  description: string;
}[];

async function getResourcePermissions({
  token,
}: GetResourcePermissionsRequest): Promise<GetResourcePermissionsResponse> {
  const response = await axiosClient.get(`/resource-permissions`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetResourcePermissions() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["resource-permissions"],
    queryFn: () => getResourcePermissions({ token }),
  });
}

export { useGetResourcePermissions };
