import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import queryClient from "@/lib/react-query";

export type Request = {
  token: string;
  folder: {
    id: number;
    name: string;
    description: string;
    parentFolderId: number | null;
  };
};

async function put({ token, folder }: Request) {
  const response = await axiosClient.put(
    `/folders/${folder.id}`,
    {
      name: folder.name,
      description: folder.description,
      parentFolderId: folder.parentFolderId,
    },
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );
  return response.data;
}

function usePutFolder(groupId: number) {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (folder: Request["folder"]) => put({ token, folder }),
    onSuccess: () => {
      queryClient.invalidateQueries(["folders"]);
      queryClient.invalidateQueries(["groups", groupId, "root-resources"]);
      queryClient.invalidateQueries(["groups", groupId]);
    },
  });
}

export { usePutFolder };
