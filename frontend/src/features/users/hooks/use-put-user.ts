import {useMutation} from "react-query";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import queryClient from "@/lib/react-query";
import putUser, {PutUserRequest} from "@/features/users/services/put-user.ts";
import {toast} from "sonner";

function usePutUser(){
  const {token, tokenPayload} = useAuthContext();

  return useMutation({
    mutationKey: ['user'],
    mutationFn: (user: PutUserRequest) => putUser(token, tokenPayload?.id as number, user),
    onSuccess: () => {
      queryClient.removeQueries(['user']);

      toast.success('Perfil atualizado com sucesso');
    }
  });
}

export {usePutUser}