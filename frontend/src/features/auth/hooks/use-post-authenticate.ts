import {useMutation} from "react-query";
import postAuthenticate from "@/features/auth/services/post-authenticate.ts";
import {useAuthContext} from "@/features/auth/hooks/use-auth-context.ts";
import {useNavigate} from "react-router-dom";

function usePostAuthenticate(){
  const navigate = useNavigate();
  const {setToken} = useAuthContext();

  return useMutation({
    mutationKey: ['authenticate'],
    mutationFn: postAuthenticate,
    onSuccess: ({token}) => {
      setToken(token);
      navigate("/groups")
    }
  });
}

export {usePostAuthenticate}