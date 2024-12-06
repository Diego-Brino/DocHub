import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useMutation } from "react-query";
import { toast } from "sonner";
import queryClient from "@/lib/react-query";

export type PostGroupRequest = {
  token: string;
  group: {
    name: string;
    description: string;
    // Add other fields as necessary
  };
};

async function postGroup({ token, group }: PostGroupRequest) {
  const formData = new FormData();

  formData.append("name", group.name);
  formData.append("description", group.description);

  const response = await axiosClient.post(`/groups`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function usePostGroup() {
  const { token } = useAuthContext();

  return useMutation({
    mutationFn: (group: PostGroupRequest["group"]) =>
      postGroup({ token, group }),
    onSuccess: () => {
      queryClient.invalidateQueries(["groups"]);
      toast.success("GroupFiles created successfully");
    },
  });
}

export { usePostGroup };
