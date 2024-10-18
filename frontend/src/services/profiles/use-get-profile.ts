import { useQuery } from "react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";

export type GetUserRequest = {
  token: string;
  id: number;
};

export type GetUserResponse = {
  id: number;
  name: string;
  email: string;
  username: string;
  avatarUrl: string;
};

async function getProfile({
  token,
  id,
}: GetUserRequest): Promise<GetUserResponse> {
  const response = await axiosClient.get(`/profiles/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetProfile({ id }: { id: number | null }) {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["user", id],
    queryFn: () => getProfile({ token, id: id as number }),
    enabled: !!id,
  });
}

export { useGetProfile };
