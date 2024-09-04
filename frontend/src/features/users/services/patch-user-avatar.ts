import axiosClient from "@/lib/axios";

async function patchUserAvatar(token: string, id: number, avatar: File): Promise<void> {
  const formData = new FormData();
  formData.append('avatar', avatar);

  await axiosClient.patch(`/users/${id}/avatar`, formData, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'multipart/form-data',
    },
  });
}

export default patchUserAvatar;