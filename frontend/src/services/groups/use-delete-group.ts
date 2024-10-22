import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type DeleteGroupRequest = {
  token: string;
  groupId: number;
};

async function deleteGroup({ token, groupId }: DeleteGroupRequest) {
  await axiosClient.delete(`/groups/${groupId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

function useDeleteGroup() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (groupId: number) => deleteGroup({ token, groupId }),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      toast.success("Group deleted successfully");
    },
  });
}

export { useDeleteGroup };
