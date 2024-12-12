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
import { useEffect, useMemo, useState } from "react";
import {
  ArrowLeft,
  ArrowRight,
  FilePlus,
  Loader,
  Paperclip,
} from "lucide-react";
import {
  ArchivePresignedUrlResponse,
  usePresignedUrlMutation,
} from "@/services/archives/use-get-archive-presigned-url-create.ts";
import { useNavigate, useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import axios from "axios";
import queryClient from "@/lib/react-query";
import axiosClient from "@/lib/axios";
import { useAuthContext } from "@/contexts/auth";
import { toast } from "sonner";
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { Separator } from "@/components/ui/separator.tsx";
import { useGetGroupFolderContentsResources } from "@/services/groups/use-get-group-folder-contents.ts";
import { useMutation, useQuery } from "react-query";
import { Flow, Process, User } from "@/pages/flow.tsx";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select.tsx";
import { Badge } from "@/components/ui/badge.tsx";
import reactQuery from "@/lib/react-query";

type Archive = {
  S3Hash: string;
  id: number;
  name: string;
  description: string;
  type: string;
  length: number;
};

function Request() {
  const { token } = useAuthContext();

  const [files, setFiles] = useState<File[]>([]);

  const { id, idRequest } = useParams();

  const { data: dataGroup } = useGetGroup(Number(id));

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

  const { data: dataGetRequest } = useQuery({
    queryKey: ["requests", id],
    queryFn: async (): Promise<{
      id: number;
      user: User;
      process: Process;
      status: string;
      movements: {
        resourceMovements: {
          archive: {
            id: number;
            name: string;
            description: string;
            S3Hash: string;
            length: number;
            type: string;
          };
        }[];
      }[];
      startDate: string;
    }> => {
      const response = await axiosClient.get(`requests/${idRequest}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    },
    enabled: !!token && !!idRequest,
  });

  async function getPresignedUrlToCreate(
    movementId: number,
    contentType: string,
  ): Promise<ArchivePresignedUrlResponse> {
    const response = await axiosClient.get(
      "/resource-movements/presigned-url/create",
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          movementId: movementId,
          contentType: contentType,
        },
      },
    );

    return response.data;
  }

  const { data: dataGetCurrentFlow } = useQuery({
    queryKey: ["current-flow"],
    queryFn: async (): Promise<Flow> => {
      const response = await axiosClient.get(
        `requests/${idRequest}/actual-flow`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );
      return response.data;
    },
    enabled: !!token && !!idRequest,
  });

  useEffect(() => {
    console.log("???", dataGetCurrentFlow);
  }, [dataGetCurrentFlow]);

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

      setFiles([]);
    } catch (error) {
      toast.error("Erro ao enviar arquivos.");
      setFiles([]);
    }
  };

  // useEffect(() => {
  //   if (files.length > 0) {
  //     handleUpload();
  //   }
  // }, [files]);

  const navigate = useNavigate();

  const [selectedDestinationFlow, setSelectedDestinationFlow] =
    useState<number>(0);

  const { mutateAsync: mutateChangeFlow } = useMutation({
    mutationFn: async () => {
      const request = axiosClient.post(
        `/movements`,
        {
          requestId: Number(idRequest),
          flowId: dataGetCurrentFlow?.id,
          responseId: selectedDestinationFlow,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        },
      );

      const response = await request;

      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries(["current-flow"]);
      queryClient.invalidateQueries(["requests"]);
      queryClient.invalidateQueries(["groups"]);
      toast.success("Fluxo avançado com sucesso.");
    },
  });

  const sendFiles = async () => {
    mutateChangeFlow()
      .then(async (response) => {
        for (const file of files) {
          const delay = (ms: number) =>
            new Promise((resolve) => setTimeout(resolve, ms));

          await delay(1000);

          await getPresignedUrlToCreate(response, file.type)
            .then(async (response2) => {
              await axios.put(response2.url, file, {
                headers: {
                  "Content-Type": file.type,
                },
              });

              await axiosClient.post(
                `/resource-movements`,
                {
                  movementId: response,
                  hashS3: response2.hashS3,
                  name: file.name.split(".")[0],
                  description: "",
                  contentType: file.type,
                  length: file.size,
                },
                {
                  headers: {
                    Authorization: `Bearer ${token}`,
                  },
                },
              );
            })
            .catch((error) => {
              console.error(error);
            });
        }

        setFiles([]);

        setMounted(!mounted);
      })
      .then(() => {
        reactQuery.invalidateQueries(["current-flow"]);
        reactQuery.invalidateQueries(["request"]);
        reactQuery.invalidateQueries(["requests"]);
      });
  };

  const [mounted, setMounted] = useState(false);

  const archives = useMemo(() => {
    const files: Archive[] = [];

    dataGetRequest?.movements.map((f) => {
      f.resourceMovements.map((r) => {
        files.push(r.archive);
      });
    });

    // const files = dataGetRequest?.movements[0].resourceMovements.map((r) => {
    //   return {
    //     id: r.archive.id,
    //     name: r.archive.name,
    //     description: r.archive.description,
    //     S3Hash: r.archive.S3Hash,
    //     length: r.archive.length,
    //     type: r.archive.type,
    //   };
    // });

    console.log(files);

    return files;
  }, [dataGetRequest, mounted]);

  return (
    <div className="flex gap-4 flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
      <GroupToolbarProvider>
        <div className="flex gap-4 items-center justify-between">
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
            <p className="text-lg font-semibold">
              Fluxo:{" "}
              <span className="text-primary">
                {dataGetRequest?.process.service.description}
              </span>
            </p>
            /
            <p className="text-lg text-muted-foreground">
              Etapa Atual:{" "}
              {dataGetCurrentFlow
                ? dataGetCurrentFlow?.activity?.description
                : "Finalizado"}
            </p>
          </div>
          <div>
            {dataGetCurrentFlow ? (
              <Badge>Tempo Restante: {dataGetCurrentFlow?.time} dias</Badge>
            ) : (
              <Badge>FINALIZADO</Badge>
            )}
          </div>
        </div>
        <GroupToolbar
          currentPath={currentPath}
          setCurrentPath={setCurrentPath}
          buttons={
            <div className="flex gap-4 w-full">
              <Select
                onValueChange={(value: string) => {
                  setSelectedDestinationFlow(Number(value));
                }}
                value={selectedDestinationFlow?.toString()}
              >
                <SelectTrigger>
                  <SelectValue placeholder={"Selecione a próxima etapa"} />
                </SelectTrigger>
                <SelectContent>
                  <SelectGroup>
                    <SelectLabel>Etapas</SelectLabel>
                    {dataGetCurrentFlow &&
                      dataGetCurrentFlow?.responseFlows.map((rf) => (
                        <SelectItem
                          key={rf.response.id}
                          value={rf.response.id.toString()}
                        >
                          {rf.response.description}
                        </SelectItem>
                      ))}
                  </SelectGroup>
                </SelectContent>
              </Select>
              <Button
                disabled={!dataGetCurrentFlow || selectedDestinationFlow === 0}
                className="flex gap-2"
                onClick={() => {
                  sendFiles();
                }}
              >
                Avançar Fluxo <ArrowRight />
              </Button>
            </div>
          }
        />
        <div className="flex flex-col items-end justify-center w-full gap-2">
          {dataGetCurrentFlow &&
          dataGetCurrentFlow?.responseFlows.length > 0 ? (
            <FileUploader
              value={files}
              onValueChange={(value: File[] | null) =>
                setFiles(value as File[])
              }
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
          ) : (
            <div className="flex items-center justify-center w-full h-32">
              <p className="text-xl text-muted-foreground">
                Fluxo Finalizado. Não é possível enviar arquivos.
              </p>
            </div>
          )}
        </div>
        <GroupResources
          other={true}
          data={{
            folders: [],
            archives: archives,
          }}
          currentPath={currentPath}
          setCurrentPath={setCurrentPath}
        />
      </GroupToolbarProvider>
    </div>
  );
}

Request.displayName = "Request";

export { Request };
