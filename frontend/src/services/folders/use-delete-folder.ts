import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type DeleteRequest = {
  token: string;
  id: number | null;
};

export type DeleteResponse = void;

async function request({ token, id }: DeleteRequest): Promise<DeleteResponse> {
  const response = await axiosClient.delete(`/folders/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useDeleteFolder({ id }: Omit<DeleteRequest, "token">) {
  const { token } = useAuthContext();

  return useMutation({
    mutationKey: ["folders", id],
    mutationFn: () => request({ token, id }),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      queryClient.invalidateQueries(["folders"]);
      toast.success("Pasta excluída com sucesso");
    },
  });
}

export { useDeleteFolder };
