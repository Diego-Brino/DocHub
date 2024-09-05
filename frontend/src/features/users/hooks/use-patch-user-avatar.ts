import {useMutation} from "react-query";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import patchUserAvatar from "@/features/users/services/patch-user-avatar.ts";
import queryClient from "@/lib/react-query";

function usePatchUserAvatar(){
  const {token, tokenPayload} = useAuthContext();

  return useMutation({
    mutationKey: ['user', 'avatar'],
    mutationFn: (avatar: File) => patchUserAvatar(token, tokenPayload?.id as number, avatar),
    onSuccess: () => {
      queryClient.invalidateQueries(['user']);
    }
  });
}

export {usePatchUserAvatar}