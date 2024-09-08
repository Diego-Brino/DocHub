import {useQuery} from "react-query";
import getUser from "@/features/users/services/get-user.ts";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";

function useGetUser(){
  const {token, tokenPayload} = useAuthContext();

  return useQuery({
    queryKey: ['user', token],
    queryFn: () => getUser(token, tokenPayload?.id as number)
  });
}

export {useGetUser}