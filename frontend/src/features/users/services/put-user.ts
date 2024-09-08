import axiosClient from "@/lib/axios";

export type PutUserRequest = {
  name: string,
  email: string,
  username: string,
}

export type PutUserResponse = {
  token: string,
}

async function putUser(token: string, id: number, user: PutUserRequest): Promise<PutUserResponse> {
  const formData = new FormData();

  formData.append('name', user.name);
  formData.append('email', user.email);
  formData.append('username', user.username);

  const response = await axiosClient.put(`/users/${id}`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
}

export default putUser;