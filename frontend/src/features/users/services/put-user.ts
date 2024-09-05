import axiosClient from "@/lib/axios";

export type PutUserRequest = {
  name: string,
  email: string,
  username: string,
}

async function putUser(token: string, id: number, user: PutUserRequest): Promise<void> {
  await axiosClient.put(`/users/${id}`, user, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export default putUser;