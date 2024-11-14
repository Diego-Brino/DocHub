import {
  Archive,
  Folder,
  useGetGroupRootResources,
} from "@/services/groups/use-get-group-root-resources.ts";
import { useParams } from "react-router-dom";
import { Card, CardDescription, CardTitle } from "@/components/ui/card.tsx";
import { File, Folder as FolderIcon } from "lucide-react";
import {
  Sheet,
  SheetContent,
  SheetDescription,
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
  return (
    <Card className="hover:bg-muted hover:cursor-pointer" onClick={onClick}>
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

const FolderCard = ({
  folder,
  onClick,
}: {
  folder: Folder;
  onClick: MouseEventHandler<HTMLDivElement> | undefined;
}) => {
  return (
    <Card className="hover:bg-muted hover:cursor-pointer" onClick={onClick}>
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

  const { mutateAsync } = useDeleteArchive();

  const { data } = useGetGroupRootResources(Number(id));

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

  return (
    <>
      <div className="w-full h-full gap-4 grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 overflow-y-scroll content-start relative mb-8 md:mb-0">
        {data?.folders.map((folder) => (
          <FolderCard key={folder.id} folder={folder} onClick={() => {}} />
        ))}
        {data?.archives.map((archive) => (
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
            <SheetTitle>{dataArchive?.name}</SheetTitle>
            <SheetDescription>{dataArchive?.description}</SheetDescription>
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
    </>
  );
}

GroupResources.displayName = "GroupResources";

export { GroupResources };
