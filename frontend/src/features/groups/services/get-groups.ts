import axiosClient from "@/lib/axios";
import { Group } from "@/features/groups/types";

async function getGroups(): Promise<Group[]> {
  const response = await axiosClient.get("/groups");
  return response.data;
}

export default getGroups;
