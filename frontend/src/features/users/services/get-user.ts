import axiosClient from "@/lib/axios";
import {User} from "@/features/auth/types";

async function getUser(token: string, id: number): Promise<User> {
  const response = await axiosClient.get(`/users/${id}`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  return response.data;
}

export default getUser;