import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type Archive = {
  S3Hash: string;
  id: number;
  name: string;
  description: string;
  type: string;
  length: number;
};

export type Folder = {
  id: number;
  name: string;
  description: string;
};

export type GetGroupRootResourcesResponse = {
  archives: Archive[];
  folders: Folder[];
};

async function getGroupRootResources(
  token: string,
  id: number | null,
): Promise<GetGroupRootResourcesResponse> {
  const response = await axiosClient.get(`/groups/${id}/root-resources`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetGroupRootResources(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups", id, "root-resources"],
    queryFn: () => getGroupRootResources(token, id),
    enabled: !!id,
  });
}

export { useGetGroupRootResources };
