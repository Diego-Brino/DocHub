import {useMutation} from "react-query";
import postAuthenticate from "@/features/auth/services/post-authenticate.ts";

function usePostAuthenticate(){
  return useMutation({
    mutationKey: ['authenticate'],
    mutationFn: postAuthenticate,
  });
}

export {usePostAuthenticate}