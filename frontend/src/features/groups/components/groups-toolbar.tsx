import { Input } from "@/components/custom/input.tsx";
import { Plus, Search } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
import { useGroupsFilterContext } from "@/features/groups/hooks/use-groups-filter-context.ts";

function GroupsToolbar() {
  const { filter, setFilter, applyFilter } = useGroupsFilterContext();

  return (
    <div className="flex items-center gap-4 border-b p-4">
      <div className="flex-1 flex justify-start items-center">
        <Input
          value={filter}
          placeholder="Filtrar..."
          endIcon={<Search className="size-5" />}
          onChange={(event) => setFilter(event.target.value)}
          onClickEndIcon={() => applyFilter()}
          onKeyDown={(event) => {
            if (event.key === "Enter") {
              applyFilter();
            }
          }}
        />
      </div>
      <div className="flex-1 flex justify-end items-center">
        <Tooltip>
          <TooltipTrigger asChild>
            <Button size="icon" variant="outline">
              <Plus className="size-5" />
            </Button>
          </TooltipTrigger>
          <TooltipContent>
            <p>Novo grupo</p>
          </TooltipContent>
        </Tooltip>
      </div>
    </div>
  );
}

GroupsToolbar.displayName = "GroupsToolbar";

export { GroupsToolbar };
