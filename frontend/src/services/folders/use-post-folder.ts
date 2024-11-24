import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type Request = {
  token: string;
  folder: {
    name: string;
    description: string;
    groupId: number;
    parentFolderId: number | null;
  };
};

async function post({ token, folder }: Request) {
  const response = await axiosClient.post(`/folders`, folder, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function usePostFolder(groupId: number) {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (folder: Request["folder"]) => post({ token, folder }),
    onSuccess: () => {
      queryClient.invalidateQueries(["folders"]);
      queryClient.invalidateQueries(["groups", groupId, "root-resources"]);
      queryClient.invalidateQueries(["groups", groupId]);
      toast.success("Pasta criada com sucesso");
    },
  });
}

export { usePostFolder };
