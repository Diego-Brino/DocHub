import {useQuery} from "react-query";
import getGroups from "@/features/groups/services/get-groups.ts";

function useGetGroups(){
  return useQuery({
    queryKey: ['groups'],
    queryFn: getGroups,
  });
}

export {useGetGroups}