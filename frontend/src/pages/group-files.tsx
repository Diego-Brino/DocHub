import {
  GroupToolbar,
  GroupToolbarProvider,
} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import { GroupResources } from "@/features/groups/group-resources/group-resources.tsx";
import {
  FileInput,
  FileUploader,
  FileUploaderContent,
  FileUploaderItem,
} from "@/components/extensions/file-input.tsx";
import { useEffect, useState } from "react";
import { ArrowLeft, FilePlus, Loader, Paperclip } from "lucide-react";
import { usePresignedUrlMutation } from "@/services/archives/use-get-archive-presigned-url-create.ts";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import axios from "axios";
import queryClient from "@/lib/react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { toast } from "sonner";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb.tsx";
import { Separator } from "@/components/ui/separator.tsx";
import {
  Archive,
  Folder,
  useGetGroupRootResources,
} from "@/services/groups/use-get-group-root-resources.ts";
import { useGetGroupFolderContentsResources } from "@/services/groups/use-get-group-folder-contents.ts";

function GroupFiles() {
  const { token } = useAuthContext();

  const [files, setFiles] = useState<File[]>([]);

  const { id } = useParams();

  const { data: dataGroup } = useGetGroup(Number(id));

  const { data: dataRoot } = useGetGroupRootResources(Number(id));

  const [currentPath, setCurrentPath] = useState<
    {
      id: number;
      name: string;
    }[]
  >([]);

  const { mutateAsync } = usePresignedUrlMutation(
    Number(id),
    files.length > 0 ? files[0].type : "",
  );

  const dropZoneConfig = {
    maxFiles: 99,
    multiple: true,
  };

  const handleUpload = async () => {
    if (!files || files.length === 0) return;

    const delay = (ms: number) =>
      new Promise((resolve) => setTimeout(resolve, ms));

    try {
      for (const file of files) {
        const toastId = toast.loading(
          <div className="flex items-center gap-2">
            <Loader className="animate-spin h-5 w-5" />
            <span>Enviando arquivos...</span>
          </div>,
        );

        const { url, hashS3 } = await mutateAsync();

        await axios.put(url, file, {
          headers: {
            "Content-Type": file.type,
          },
        });

        await axiosClient.post(
          `/archives`,
          {
            hashS3,
            name: file.name.split(".")[0],
            description: "",
            groupId: id,
            folderId:
              currentPath.length > 0
                ? currentPath[currentPath.length - 1].id
                : null,
            contentType: file.type,
            length: file.size,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          },
        );

        toast.success(`Arquivo "${file.name}" enviado com sucesso.`, {
          id: toastId,
        });

        await delay(1000);
      }

      queryClient.invalidateQueries(["archives"]);
      queryClient.invalidateQueries(["folders"]);
      queryClient.invalidateQueries(["groups"]);

      setFiles([]); // Clear the file list
    } catch (error) {
      toast.error("Erro ao enviar arquivos."); // Notify overall failure
      setFiles([]); // Remove the file that failed
    }
  };

  useEffect(() => {
    if (files.length > 0) {
      handleUpload();
    }
  }, [files]);

  const { data: dataFolderContents } = useGetGroupFolderContentsResources(
    Number(id),
    currentPath.length > 0 ? currentPath[currentPath.length - 1].id : null,
  );

  const navigate = useNavigate();

  return (
    <div className="flex gap-4 flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
      <GroupToolbarProvider>
        <div className="flex gap-4 items-center">
          <Button
            size={`icon`}
            onClick={() => {
              navigate(-1);
            }}
          >
            <ArrowLeft />
          </Button>
          <h1 className="text-xl md:text-3xl font-semibold">
            {dataGroup?.name}
          </h1>
          <Separator orientation="vertical" />
          <Breadcrumb>
            <BreadcrumbList>
              <BreadcrumbItem>
                <BreadcrumbLink
                  className="cursor-pointer"
                  onClick={() => {
                    setCurrentPath([]);
                  }}
                >
                  Raiz
                </BreadcrumbLink>
              </BreadcrumbItem>
              {currentPath.map((path, i) => (
                <>
                  <BreadcrumbSeparator />
                  <BreadcrumbItem key={i}>
                    <BreadcrumbLink
                      className="cursor-pointer"
                      onClick={() => {
                        setCurrentPath([...currentPath.slice(0, i + 1)]);
                      }}
                    >
                      {path.name}
                    </BreadcrumbLink>
                  </BreadcrumbItem>
                </>
              ))}
            </BreadcrumbList>
          </Breadcrumb>
        </div>
        <GroupToolbar
          currentPath={currentPath}
          setCurrentPath={setCurrentPath}
        />
        <div className="flex flex-col items-end justify-center w-full gap-2">
          <FileUploader
            value={files}
            onValueChange={(value: File[] | null) => setFiles(value as File[])}
            dropzoneOptions={dropZoneConfig}
            className="flex flex-col p-[1px] items-center justify-center pb-1.5"
          >
            <FileInput className="outline-dashed outline-1 outline-white">
              <div className="flex items-center justify-center flex-col pt-3 pb-4 w-full ">
                <FilePlus className="h-8 w-8 stroke-current" />
                <span className="text-muted-foreground text-sm">
                  Arraste e solte arquivos aqui
                </span>
              </div>
            </FileInput>
            <FileUploaderContent>
              {files &&
                files.length > 0 &&
                files.map((file, i) => (
                  <FileUploaderItem key={i} index={i}>
                    <Paperclip className="h-4 w-4 stroke-current" />
                    <span>{file.name}</span>
                  </FileUploaderItem>
                ))}
            </FileUploaderContent>
          </FileUploader>
        </div>
        <GroupResources
          data={
            currentPath.length > 0
              ? (dataFolderContents as {
                  archives: Archive[];
                  folders: Folder[];
                })
              : (dataRoot as {
                  archives: Archive[];
                  folders: Folder[];
                })
          }
          currentPath={currentPath}
          setCurrentPath={setCurrentPath}
        />
      </GroupToolbarProvider>
    </div>
  );
}

GroupFiles.displayName = "GroupFiles";

export { GroupFiles };
