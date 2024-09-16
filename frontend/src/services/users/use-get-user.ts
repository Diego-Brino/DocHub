import {useQuery} from "react-query";
import axiosClient from "@/lib/axios";
import {useAuthContext} from "@/contexts/auth";

export type GetUserRequest = {
  token: string;
  id: number;
}

export type GetUserResponse = {
  id: number,
  name: string,
  email: string,
  username: string,
  avatarUrl: string
}

async function getUser({token, id}: GetUserRequest): Promise<GetUserResponse> {
  const response = await axiosClient.get(`/users/${id}`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  return response.data;
}

function useGetUser() {
  const {token, tokenPayload} = useAuthContext();

  return useQuery({
    queryKey: ['user', token],
    queryFn: () => getUser({token, id: tokenPayload?.id as number})
  });
}

export {useGetUser}