import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

// Define a function to get the file from the server
async function getArchiveFile(token: string, id: number | null): Promise<Blob> {
  const response = await axiosClient.get(`/archives/${id}/file`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    responseType: "blob", // Important to specify blob type for binary data
  });

  return response.data;
}

function useGetArchiveFile(id: number | null) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["archives", id, "file"],
    queryFn: () => getArchiveFile(token, id),
    enabled: !!id,
  });
}

export { useGetArchiveFile };
