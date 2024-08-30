import axiosClient from "@/lib/axios";

async function getUserAvatar(token: string, id: number): Promise<File> {
  const response = await axiosClient.get(`/users/${id}/avatar`, {
    headers: {
      'Authorization': `Bearer ${token}`
    },
    responseType: 'blob'
  });

  return response.data;
}

export default getUserAvatar;