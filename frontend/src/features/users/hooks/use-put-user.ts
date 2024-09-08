import {useMutation} from "react-query";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import putUser, {PutUserRequest} from "@/features/users/services/put-user.ts";
import {toast} from "sonner";

function usePutUser(){
  const {token, tokenPayload, setToken} = useAuthContext();

  return useMutation({
    mutationKey: ['user'],
    mutationFn: (user: PutUserRequest) => putUser(token, tokenPayload?.id as number, user),
    onSuccess: (res) => {
      setToken(res.token);
      toast.success('Perfil atualizado com sucesso');
    }
  });
}

export {usePutUser}