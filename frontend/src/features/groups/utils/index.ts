import { Group } from "@/features/groups/types";

function filterGroups(filter: string, groups: Group[]) {
  return groups.filter((group) => {
    return group.title.includes(filter) || group.description.includes(filter);
  });
}

export { filterGroups };
