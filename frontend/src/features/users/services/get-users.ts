import axiosClient from "@/lib/axios";

export type GetUsersResponse = {
  id: number,
  name: string,
  email: string,
  username: string,
  avatarUrl: string
}

async function getUsers(): Promise<GetUsersResponse[]> {
  const response = await axiosClient.get('/users');
  return response.data;
}

export default getUsers;