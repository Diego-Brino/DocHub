import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetGroupsResponse = {
  id: number;
  name: string;
  description: string;
  groupUrl: string; // Added based on GroupResponseDTO
}[];

async function getGroups(token: string): Promise<GetGroupsResponse> {
  const response = await axiosClient.get(`/groups`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetGroups() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups"],
    queryFn: () => getGroups(token),
  });
}

export { useGetGroups };
