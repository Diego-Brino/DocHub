import axiosClient from "@/lib/axios";

export type GetUserRequest = {
  id: number
}

export type GetUserResponse = {
  id: number,
  name: string,
  email: string,
  username: string,
  avatarUrl: string
}

async function getUser({id}: GetUserRequest): Promise<GetUserResponse> {
  const response = await axiosClient.get(`/users/${id}`);
  return response.data;
}

export default getUser;