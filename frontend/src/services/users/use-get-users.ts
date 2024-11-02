import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { useQuery } from "react-query";

export type GetUsersRequest = {
  token: string;
};

export type GetUsersResponse = {
  id: number;
  name: string;
  email: string;
  username: string;
  avatarUrl: string;
  lastAccess: string;
};

async function getUsers({
  token,
}: GetUsersRequest): Promise<GetUsersResponse[]> {
  const response = await axiosClient.get("/users", {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
}

function useGetUsers() {
  const { token } = useAuthContext();

  return useQuery({
    queryKey: ["users", token],
    queryFn: () => getUsers({ token }),
  });
}

export { useGetUsers };
