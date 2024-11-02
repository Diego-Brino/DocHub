import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetGroupResourcesResponse = {
  id: number;
  name: string;
  description: string;
  type: "Arquivo" | "Pasta";
  group: {
    id: number;
    name: string;
    description: string;
    groupUrl: string;
    idS3Bucket: string;
  };
}[];

async function getGroupResources(
  token: string,
  id: number | null,
): Promise<GetGroupResourcesResponse> {
  const response = await axiosClient.get(`/groups/${id}/resources`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetGroupResources(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups", id, "resources"],
    queryFn: () => getGroupResources(token, id),
    enabled: !!id,
  });
}

export { useGetGroupResources };
