import {
  Archive,
  Folder,
} from "@/services/groups/use-get-group-root-resources.ts";
import { Card, CardTitle } from "@/components/ui/card.tsx";
import { File, Folder as FolderIcon, Undo2 } from "lucide-react";
import {
  Sheet,
  SheetContent,
  SheetFooter,
  SheetHeader,
  SheetTitle,
} from "@/components/ui/sheet.tsx";
import { MouseEventHandler, useEffect, useState } from "react";
import { useGetArchive } from "@/services/archives/use-get-archive.ts";
import { useGetArchiveFile } from "@/services/archives/use-get-archive-file.ts";
import { Button } from "@/components/custom/button.tsx";
import { useDeleteArchive } from "@/services/archives/use-delete-archive.ts";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog.tsx";
import { useGroupToolbarContext } from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { useDeleteFolder } from "@/services/folders/use-delete-folder.ts";
import { usePutFolder } from "@/services/folders/use-put-folder.ts";
import { useParams } from "react-router-dom";
import { usePutArchive } from "@/services/archives/use-put-archive.ts";
import {
  RolePermissionsDialog,
  RolePermissionsDialogProvider,
} from "@/features/roles/role-permissions-dialog/role-permissions-dialog";
import {
  Tooltip,
  TooltipContent,
  TooltipTrigger,
} from "@/components/ui/tooltip.tsx";

function getFileExtension(mimeType: string) {
  switch (mimeType) {
    case "image/jpeg":
      return "jpg";
    case "image/png":
      return "png";
    case "video/mp4":
      return "mp4";
    case "application/pdf":
      return "pdf";
    case "application/msword":
      return "doc";
    case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
      return "docx";
    case "application/vnd.ms-excel":
      return "xls";
    case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
      return "xlsx";
    case "application/vnd.ms-powerpoint":
      return "ppt";
    case "application/x-zip-compressed":
      return "zip";
    case "application/x-rar-compressed":
      return "rar";
    case "application/x-7z-compressed":
      return "7z";
    default:
      return "bin";
  }
}

function ArchiveCard({
  archive,
  onClick,
}: {
  archive: Archive;
  onClick: MouseEventHandler<HTMLDivElement> | undefined;
}) {
  const [isOpen, setIsOpen] = useState(false);

  const { mutateAsync: deleteFile } = useDeleteArchive();

  return (
    <>
      <Card
        className="hover:bg-muted hover:cursor-pointer"
        onClick={onClick}
        draggable
        onDragStart={(e) => {
          e.dataTransfer.setData(
            "application/json",
            JSON.stringify({ type: "archive", id: archive.id }),
          );
        }}
      >
        <div className="p-4 flex gap-4 items-center justify-between overflow-hidden relative">
          <File className="size-8 min-h-8 min-w-8" />
          <Tooltip>
            <TooltipTrigger
              className={"overflow-hidden flex justify-start w-full"}
            >
              <CardTitle className="text-xl text-ellipsis overflow-hidden whitespace-nowrap max-w-40">
                {archive.name + "." + getFileExtension(archive.type)}
              </CardTitle>
            </TooltipTrigger>
            <TooltipContent>
              {archive.name + "." + getFileExtension(archive.type)}
            </TooltipContent>
          </Tooltip>
        </div>
      </Card>
      <AlertDialog
        open={isOpen}
        onOpenChange={() => {
          setIsOpen(false);
        }}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja excluir este arquivo?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={() => deleteFile(archive.id)}>
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
}

const FolderCard = ({
  folder,
  onClick,
  onDropFile,
}: {
  folder: Folder;
  onClick: MouseEventHandler<HTMLDivElement> | undefined;
  onDropFile: (
    resource: {
      type: "folder" | "archive";
      id: number;
    },
    folderId: number,
  ) => void;
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const { mutateAsync: deleteFolder } = useDeleteFolder({
    id: folder.id,
  });

  //const { open, setSelectedResource } = useRolePermissionsDialogContext();

  return (
    <>
      <Card
        className="hover:bg-muted hover:cursor-pointer"
        onClick={onClick}
        draggable
        onDragStart={(e) => {
          e.dataTransfer.setData(
            "application/json",
            JSON.stringify({ type: "folder", id: folder.id }),
          );
        }}
        onDragOver={(e) => {
          e.preventDefault();
        }}
        onDrop={(e) => {
          e.preventDefault();
          const data = JSON.parse(e.dataTransfer.getData("application/json"));
          onDropFile(data, folder.id);
        }}
      >
        <div className="p-4 flex gap-4 items-center justify-between overflow-hidden relative">
          <div className="flex gap-4 items-center">
            <FolderIcon className="size-8 min-h-8 min-w-8" />
            <div className="overflow-hidden">
              <CardTitle className="text-xl text-ellipsis overflow-hidden whitespace-nowrap max-w-40">
                {folder.name}
              </CardTitle>
            </div>
          </div>
        </div>
      </Card>
      <AlertDialog
        open={isOpen}
        onOpenChange={() => {
          setIsOpen(false);
        }}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja excluir esta pasta?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction onClick={() => deleteFolder()}>
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
      <RolePermissionsDialog />
    </>
  );
};

function GroupResources({
  data,
  currentPath,
  setCurrentPath,
}: {
  data: {
    folders: Folder[];
    archives: Archive[];
  };
  currentPath: { id: number; name: string }[];
  setCurrentPath: (path: { id: number; name: string }[]) => void;
}) {
  const { id } = useParams();

  const { mutateAsync: mutateAsyncPutFolder } = usePutFolder(Number(id));
  const { mutateAsync: mutateAsyncPutArchive } = usePutArchive(Number(id));

  const onMoveResource = (
    resource: {
      type: "folder" | "archive";
      id: number;
    },
    folderId: number | null,
  ) => {
    if (resource.type === "folder") {
      mutateAsyncPutFolder({
        id: resource.id,
        parentFolderId: folderId,
      });
    } else {
      mutateAsyncPutArchive({
        id: resource.id,
        folderId: folderId,
      });
    }
  };

  const { mutateAsync } = useDeleteArchive();

  const [isAlertOpen, setIsAlertOpen] = useState(false);

  const [isOpen, setIsOpen] = useState(false);

  const [selectedResourceId, setSelectedResource] = useState<number | null>(
    null,
  );

  const { data: dataArchive } = useGetArchive(selectedResourceId);

  const { data: fileBlob } = useGetArchiveFile(dataArchive?.id || null);
  const [fileUrl, setFileUrl] = useState<string | null>(null);

  useEffect(() => {
    if (fileBlob) {
      const url = URL.createObjectURL(fileBlob);
      setFileUrl(url);

      return () => URL.revokeObjectURL(url);
    }
  }, [fileBlob]);

  const { appliedFilter } = useGroupToolbarContext();

  const parentFolderId = currentPath[currentPath.length - 1]?.id || null;

  return (
    <RolePermissionsDialogProvider resourceFlag>
      <div className="w-full h-full gap-4 grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 overflow-y-scroll content-start relative mb-8 md:mb-0">
        {parentFolderId && (
          <Card
            className="hover:bg-muted hover:cursor-pointer"
            onDragOver={(e) => e.preventDefault()}
            onDrop={(e) => {
              e.preventDefault();
              const data = JSON.parse(
                e.dataTransfer.getData("application/json"),
              );
              onMoveResource(
                data,
                currentPath[currentPath.length - 2]?.id || null,
              );
            }}
            onClick={
              currentPath.length > 0
                ? () => {
                    setCurrentPath(currentPath.slice(0, -1));
                  }
                : undefined
            }
          >
            <div className="p-4 flex gap-4 items-center justify-between overflow-hidden relative">
              <div className="flex gap-4 items-center">
                <Undo2 className="size-8 min-h-8 min-w-8" />
                <div className="overflow-hidden">
                  <CardTitle className="text-xl text-ellipsis overflow-hidden whitespace-nowrap max-w-40">
                    Voltar
                  </CardTitle>
                </div>
              </div>
            </div>
          </Card>
        )}
        {data?.folders
          .filter((f) =>
            f.name.toLowerCase().includes(appliedFilter.toLowerCase()),
          )
          .map((folder) => (
            <FolderCard
              key={folder.id}
              folder={folder}
              onClick={() => {
                setCurrentPath([
                  ...currentPath,
                  {
                    id: folder.id,
                    name: folder.name,
                  },
                ]);
              }}
              onDropFile={onMoveResource}
            />
          ))}
        {data?.archives
          .filter((f) =>
            f.name.toLowerCase().includes(appliedFilter.toLowerCase()),
          )
          .map((archive) => (
            <ArchiveCard
              key={archive.id}
              archive={archive}
              onClick={() => {
                setIsOpen(true);
                setSelectedResource(archive.id);
              }}
            />
          ))}
      </div>
      <Sheet open={isOpen} onOpenChange={(open) => setIsOpen(open)}>
        <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
          <SheetHeader>
            <SheetTitle className={"text-wrap"}>
              {dataArchive?.name +
                "." +
                getFileExtension(dataArchive?.type as string)}
            </SheetTitle>
          </SheetHeader>
          <div className="py-4 flex flex-col gap-4">
            {fileBlob &&
              (() => {
                switch (true) {
                  case fileBlob.type.startsWith("image/"):
                    return (
                      <img
                        src={fileUrl || undefined}
                        alt="Preview"
                        className="w-full h-auto"
                      />
                    );
                  case fileBlob.type.startsWith("video/"):
                    return (
                      <video controls className="w-full">
                        <source
                          src={fileUrl || undefined}
                          type={fileBlob.type}
                        />
                      </video>
                    );
                  case fileBlob.type === "application/pdf":
                    return (
                      <iframe
                        src={fileUrl || undefined}
                        className="w-full h-[500px]"
                        title="PDF Viewer"
                      />
                    );
                  default:
                    return (
                      <p className="text-center text-gray-500">
                        Essa visualização não é suportada. Baixe o arquivo para
                        visualizá-lo.
                      </p>
                    );
                }
              })()}
          </div>
          <SheetFooter className="flex flex-col sm:flex-col gap-4">
            <Button asChild className="w-full">
              <a
                href={fileUrl || undefined}
                download={`${dataArchive?.name || "file"}.${getFileExtension(fileBlob?.type || "")}`}
              >
                Baixar
              </a>
            </Button>
            <Button
              variant={"destructive"}
              className="w-full !m-0"
              onClick={() => {
                setIsAlertOpen(true);
              }}
            >
              Remover
            </Button>
          </SheetFooter>
        </SheetContent>
      </Sheet>
      <AlertDialog
        open={isAlertOpen}
        onOpenChange={(open) => setIsAlertOpen(open)}
      >
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Exclusão</AlertDialogTitle>
            <AlertDialogDescription>
              Tem certeza de que deseja excluir este arquivo?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Cancelar</AlertDialogCancel>
            <AlertDialogAction
              onClick={() => {
                mutateAsync(selectedResourceId as number).finally(() => {
                  setIsAlertOpen(false);
                  setIsOpen(false);
                });
              }}
            >
              Confirmar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </RolePermissionsDialogProvider>
  );
}

GroupResources.displayName = "GroupResources";

export { GroupResources };
