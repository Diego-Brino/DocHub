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

export type GetGroupFolderContentsResponse = {
  archives: Archive[];
  folders: Folder[];
};

async function getGroupFolderContentsResources(
  token: string,
  id: number | null,
  idFolder: number | null,
): Promise<GetGroupFolderContentsResponse> {
  const response = await axiosClient.get(
    `/groups/${id}/folder/${idFolder}/contents`,
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );

  return response.data;
}

function useGetGroupFolderContentsResources(
  id: number | null,
  idFolder: number | null,
) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["groups", id, "folder", idFolder, "contents"],
    queryFn: () => getGroupFolderContentsResources(token, id, idFolder),
    onError: (error) => {
      console.error(error);
    },
    enabled: !!id && !!idFolder,
  });
}

export { useGetGroupFolderContentsResources };
