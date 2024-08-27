import {useQuery} from "react-query";
import getGroups from "@/features/groups/services/get-groups.ts";
import getUser from "@/features/users/services/get-user.ts";

function useGetUser(){
  return useQuery({
    queryKey: ['user'],
    queryFn: getUser(),
  });
}

export {useGetUser}