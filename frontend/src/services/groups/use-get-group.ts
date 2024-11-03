import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetGroupResponse = {
  id: number | null;
  name: string;
  description: string;
  groupUrl: string;
};

async function getGroup(
  token: string,
  id: number | null,
): Promise<GetGroupResponse> {
  const response = await axiosClient.get(`/groups/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetGroup(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups", id],
    queryFn: () => getGroup(token, id),
    enabled: !!id,
  });
}

export { useGetGroup };
