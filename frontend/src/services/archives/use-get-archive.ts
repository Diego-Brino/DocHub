import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

// Define the ArchiveResponseDTO type based on your endpoint's response
export type ArchiveResponseDTO = {
  S3Hash: string;
  id: number | null;
  name: string;
  description: string;
  type: string;
  length: number | null;
};

async function getArchive(
  token: string,
  id: number | null,
): Promise<ArchiveResponseDTO> {
  const response = await axiosClient.get(`/archives/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

function useGetArchive(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["archives", id],
    queryFn: () => getArchive(token, id),
    enabled: !!id,
  });
}

export { useGetArchive };
