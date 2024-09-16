import {useMutation} from "react-query";
import axiosClient from "@/lib/axios";

export type PostAuthenticateRequest = {
  email: string,
  password: string
}

export type PostAuthenticateResponse = {
  token: string
}


async function postAuthenticate(data: PostAuthenticateRequest): Promise<PostAuthenticateResponse>{
  const response = await axiosClient.post('/auth/authenticate', data);
  return response.data;
}

function usePostAuthenticate(){
  return useMutation({
    mutationKey: ['authenticate'],
    mutationFn: postAuthenticate,
  });
}

export {usePostAuthenticate}