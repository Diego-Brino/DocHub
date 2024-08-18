import axiosClient from "@/lib/axios";
import {Authentication, Token} from "@/features/auth/types";

async function postAuthenticate(data: Authentication): Promise<Token>{
  const response = await axiosClient.post('/auth/authenticate', data);
  return response.data;
}

export default postAuthenticate;