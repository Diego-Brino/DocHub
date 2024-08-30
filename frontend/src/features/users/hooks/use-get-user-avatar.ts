import {useQuery} from "react-query";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import getUserAvatar from "@/features/users/services/get-user-avatar.ts";

function useGetUserAvatar(){
  const {token, tokenPayload} = useAuthContext();

  return useQuery({
    queryKey: ['user', 'avatar'],
    queryFn: () => getUserAvatar(token, tokenPayload?.id as number)
  });
}

export {useGetUserAvatar}