import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type Request = {
  token: string;
  archiveId: number;
};

async function request({ token, archiveId }: Request) {
  await axiosClient.delete(`/archives/${archiveId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

function useDeleteArchive() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (archiveId: number) => request({ token, archiveId }),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      //queryClient.invalidateQueries(["folders"]);
      //queryClient.invalidateQueries(["archives"]);
      toast.success("Arquivo excluído com sucesso.");
    },
  });
}

export { useDeleteArchive };
