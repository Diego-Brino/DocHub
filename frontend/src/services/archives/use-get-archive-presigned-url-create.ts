import axiosClient from "@/lib/axios";
import {useAuthContext} from "@/contexts/auth";
import {useMutation} from "react-query";

export type ArchivePresignedUrlResponse = {
  url: string;
  hashS3: string;
};

async function getPresignedUrlToCreate(
  token: string,
  groupId: number,
  contentType: string
): Promise<ArchivePresignedUrlResponse> {
  const response = await axiosClient.get("/archives/presigned-url/create", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    params: {
      groupId: groupId,
      contentType: contentType
    },
  });

  return response.data;
}

const usePresignedUrlMutation = (groupId: number, contentType: string) => {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: () => getPresignedUrlToCreate(token, Number(groupId), contentType),
  });
};

export { usePresignedUrlMutation };
