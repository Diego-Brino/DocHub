import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetGroupHistoryResponse = {
  id: number;
  actionType: "CRIADO" | "EDITADO" | "DELETADO";
  description: string;
  actionUser: string;
  actionDate: string;
}[];

async function getGroupHistory(
  token: string,
  id: number | null,
): Promise<GetGroupHistoryResponse> {
  const response = await axiosClient.get(`/groups/${id}/resource-histories`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetGroupHistory(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups", id, "history"],
    queryFn: () => getGroupHistory(token, id),
    enabled: !!id,
  });
}

export { useGetGroupHistory };
