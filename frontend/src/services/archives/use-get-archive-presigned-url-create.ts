import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";

export type ArchivePresignedUrlResponse = {
  url: string;
  hashS3: string;
};

async function getPresignedUrlToCreate(
  token: string,
  groupId: number,
): Promise<ArchivePresignedUrlResponse> {
  const response = await axiosClient.get("/archives/presigned-url/create", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
    params: {
      groupId: groupId,
    },
  });

  return response.data;
}

const usePresignedUrlMutation = (groupId: number) => {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: () => getPresignedUrlToCreate(token, Number(groupId)),
  });
};

export { usePresignedUrlMutation };
