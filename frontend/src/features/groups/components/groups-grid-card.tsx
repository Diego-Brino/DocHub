import { ScrollArea } from "@/components/ui/scroll-area.tsx";
import { Button } from "@/components/custom/button.tsx";
import { ArrowRight, Edit, Trash2 } from "lucide-react";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";
import { Group } from "@/features/groups/types";
import { useState } from "react";
import { motion } from "framer-motion";

type GroupsGridCardProps = {
  group: Group;
};

function GroupsGridCard({ group }: GroupsGridCardProps) {
  const [isAvatarLoaded, setIsAvatarLoaded] = useState(false);

  return (
    <motion.div
      layout
      animate={{ opacity: 1 }}
      initial={{ opacity: 0 }}
      exit={{ opacity: 0 }}
      className="flex flex-col p-4 gap-4 rounded-lg bg-card border md:overflow-hidden min-w-32 w-full md:h-[30rem]"
    >
      <img
        src={group.avatarUrl}
        className={`h-64 w-full object-cover rounded-sm transition-opacity duration-500 ${isAvatarLoaded ? "opacity-100" : "opacity-0"}`}
        alt="avatar"
        onLoad={() => setIsAvatarLoaded(true)}
      />
      <div className="flex flex-col gap-4 flex-1 overflow-auto md:overflow-hidden">
        <h1 className="text-xl font-semibold tracking-tight">{group.title}</h1>
        <ScrollArea className="flex-1">
          <p className="text-muted-foreground">{group.description}</p>
        </ScrollArea>
        <div className="flex justify-end gap-2">
          <Tooltip>
            <TooltipTrigger asChild>
              <Button variant="outline" size="icon">
                <Trash2 className="size-5" />
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Remover grupo</p>
            </TooltipContent>
          </Tooltip>
          <Tooltip>
            <TooltipTrigger asChild>
              <Button variant="outline" size="icon">
                <Edit className="size-5" />
              </Button>
            </TooltipTrigger>
            <TooltipContent>
              <p>Editar grupo</p>
            </TooltipContent>
          </Tooltip>
          <Button className="flex items-center" variant="outline">
            Acessar <ArrowRight className="ml-2 size-5" />
          </Button>
        </div>
      </div>
    </motion.div>
  );
}

GroupsGridCard.displayName = "GroupsGridCard";

export { GroupsGridCard };
