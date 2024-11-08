import {
  Archive,
  Folder,
  useGetGroupRootResources,
} from "@/services/groups/use-get-group-root-resources.ts";
import { useParams } from "react-router-dom";
import { Card, CardDescription, CardTitle } from "@/components/ui/card.tsx";
import { File, Folder as FolderIcon } from "lucide-react";

function ArchiveCard({ archive }: { archive: Archive }) {
  return (
    <Card>
      <div className="p-4 flex gap-4 items-center">
        <File className="size-8 min-h-8 min-w-8" />
        <div className="overflow-hidden">
          <CardTitle className="text-xl">{archive.name}</CardTitle>
          <CardDescription className="whitespace-nowrap overflow-hidden text-ellipsis">
            {archive.description}
          </CardDescription>
        </div>
      </div>
    </Card>
  );
}

const FolderCard = ({ folder }: { folder: Folder }) => {
  return (
    <Card>
      <div className="p-4 flex gap-4 items-center">
        <FolderIcon className="size-8 min-h-8 min-w-8" />
        <div className="overflow-hidden">
          <CardTitle className="text-xl">{folder.name}</CardTitle>
          <CardDescription className="whitespace-nowrap overflow-hidden text-ellipsis">
            {folder.description}
          </CardDescription>
        </div>
      </div>
    </Card>
  );
};

function GroupResources() {
  const { id } = useParams();

  const { data } = useGetGroupRootResources(Number(id));

  return (
    <div className="w-full h-full gap-4 grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 overflow-y-scroll content-start relative mb-8 md:mb-0">
      {data?.folders.map((folder) => (
        <FolderCard key={folder.id} folder={folder} />
      ))}
      {data?.archives.map((archive) => (
        <ArchiveCard key={archive.id} archive={archive} />
      ))}
    </div>
  );
}

GroupResources.displayName = "GroupResources";

export { GroupResources };
