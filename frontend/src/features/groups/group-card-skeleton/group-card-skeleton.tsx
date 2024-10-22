import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
  CardDescription,
} from "@/components/ui/card.tsx";
import { Skeleton } from "@/components/ui/skeleton.tsx";
import { motion } from "framer-motion";

function GroupCardSkeleton() {
  return (
    <motion.div
      layout
      animate={{ opacity: 1 }}
      initial={{ opacity: 0 }}
      exit={{ opacity: 0 }}
    >
      <Card className="h-[167px]">
        <CardHeader className="flex flex-row justify-between">
          <div className="flex flex-col gap-2">
            <CardTitle>
              <Skeleton className="h-6 w-3/4" />
            </CardTitle>
            <CardDescription>
              <Skeleton className="h-4 w-1/2" />
            </CardDescription>
          </div>
          <Skeleton className="h-8 w-16" />
        </CardHeader>
        <CardContent>
          <div className="flex justify-between items-center">
            <div className="flex items-center gap-2">
              <Skeleton className="h-8 w-8 rounded-full" />
              <Skeleton className="h-8 w-8 rounded-full" />
            </div>
          </div>
        </CardContent>
      </Card>
    </motion.div>
  );
}

GroupCardSkeleton.displayName = "GroupCardSkeleton";

export { GroupCardSkeleton };
