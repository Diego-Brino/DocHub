import {useQuery} from "react-query";
import {useAuthContext} from "@/contexts/auth/use-auth-context.ts";
import axiosClient from "@/lib/axios";

export type GetUserAvatarRequest = {
  token: string;
  id: number;
}

export type GetUserAvatarResponse = File

async function getUserAvatar({token, id}: GetUserAvatarRequest): Promise<GetUserAvatarResponse> {
  const response = await axiosClient.get(`/users/${id}/avatar`, {
    headers: {
      'Authorization': `Bearer ${token}`
    },
    responseType: 'blob'
  });

  return response.data;
}

function useGetUserAvatar(){
  const {token, tokenPayload} = useAuthContext();

  return useQuery({
    queryKey: ['user', 'avatar'],
    queryFn: () => getUserAvatar({token, id: tokenPayload?.id as number})
  });
}

export {useGetUserAvatar}