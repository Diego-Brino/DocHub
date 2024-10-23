import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type PutGroupRequest = {
  token: string;
  groupId: number;
  group: {
    name: string;
    description: string;
  };
};

async function putGroup({ token, groupId, group }: PutGroupRequest) {
  const formData = new FormData();

  formData.append("name", group.name);
  formData.append("description", group.description);

  await axiosClient.put(`/groups/${groupId}`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

function usePutGroup() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (data: { groupId: number; group: PutGroupRequest["group"] }) =>
      putGroup({ token, groupId: data.groupId, group: data.group }),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      toast.success("Grupo atualizado com sucesso");
    },
  });
}

export { usePutGroup };
