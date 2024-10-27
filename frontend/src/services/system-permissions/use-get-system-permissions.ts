import { useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type GetSystemPermissionsRequest = {
  token: string;
};

export type GetSystemPermissionsResponse = {
  id: number;
  description: string;
}[];

async function getSystemPermissions({
  token,
}: GetSystemPermissionsRequest): Promise<GetSystemPermissionsResponse> {
  const response = await axiosClient.get(`/system-permissions`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetSystemPermissions() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["system-permissions"],
    queryFn: () => getSystemPermissions({ token }),
  });
}

export { useGetSystemPermissions };
