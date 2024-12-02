import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import queryClient from "@/lib/react-query";

export type Request = {
  token: string;
  archive: {
    id: number;
    name: string;
    description: string;
    folderId: number | null;
    contentType: string;
    length: number | null;
  };
};

async function put({ token, archive }: Request) {
  const response = await axiosClient.put(
    `/archives/${archive.id}`,
    {
      name: archive.name,
      description: archive.description,
      folderId: archive.folderId,
      contentType: archive.contentType,
      length: archive.length,
    },
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    },
  );
  return response.data;
}

function usePutArchive(groupId: number) {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (archive: Request["archive"]) => put({ token, archive }),
    onSuccess: () => {
      queryClient.invalidateQueries(["folders"]);
      queryClient.invalidateQueries(["groups", groupId, "root-resources"]);
      queryClient.invalidateQueries(["groups", groupId]);
    },
  });
}

export { usePutArchive };
