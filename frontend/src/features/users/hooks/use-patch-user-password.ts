import {useMutation} from "react-query";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import {toast} from "sonner";
import patchUserPassword, {PatchUserPasswordRequest} from "@/features/users/services/patch-user-password.ts";

function usePatchUserPassword(){
  const {token, tokenPayload, setToken} = useAuthContext();

  return useMutation({
    mutationKey: ['user', token, 'password'],
    mutationFn: (data: PatchUserPasswordRequest) => patchUserPassword(token, tokenPayload?.id as number, data),
    onSuccess: (res) => {
      setToken(res.token);
      toast.success('Senha alterada com sucesso');
    }
  });
}

export {usePatchUserPassword}