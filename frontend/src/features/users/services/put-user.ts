import axiosClient from "@/lib/axios";

export type PutUserRequest = {
  name: string,
  email: string,
  username: string,
}

async function putUser(token: string, id: number, user: PutUserRequest): Promise<void> {
  const formData = new FormData();

  formData.append('name', user.name);
  formData.append('email', user.email);
  formData.append('username', user.username);

  await axiosClient.put(`/users/${id}`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
}

export default putUser;