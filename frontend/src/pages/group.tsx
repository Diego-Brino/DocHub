import {GroupToolbar, GroupToolbarProvider,} from "@/features/groups/group-toolbar/group-toolbar.tsx";
import {GroupResources} from "@/features/groups/group-resources/group-resources.tsx";
import {FileInput, FileUploader, FileUploaderContent, FileUploaderItem,} from "@/components/extensions/file-input.tsx";
import {useState} from "react";
import {FilePlus, Paperclip} from "lucide-react";
import {usePresignedUrlMutation} from "@/services/archives/use-get-archive-presigned-url-create.ts";
import {useParams} from "react-router-dom";
import {Button} from "@/components/custom/button.tsx";
import axios from "axios";
import queryClient from "@/lib/react-query";
import axiosClient from "@/lib/axios";
import {useAuthContext} from "@/contexts/auth";
import {toast} from "sonner";

function Group() {
    const {token} = useAuthContext();

    const [files, setFiles] = useState<File[]>([]);

    const {id} = useParams();

    const [isUploading, setIsUploading] = useState(false);

    const {mutateAsync} = usePresignedUrlMutation(Number(id), files.length > 0 ? files[0].type : "");

    const dropZoneConfig = {
        maxFiles: 1,
        maxSize: 1024 * 1024 * 4,
        multiple: false,
    };

    const handleUpload = async () => {
        if (!files || files.length === 0) return;

        try {
            setIsUploading(true);
            const {url, hashS3} = await mutateAsync();
            const file = files[0];

            const req = axios.put(url, file, {
                headers: {
                    "Content-Type": file.type
                },
            });

            req.then(() => {
                axiosClient.post(`/archives`, {
                    hashS3,
                    name: file.name.split(".")[0],
                    description: "",
                    groupId: id,
                    folderId: null,
                    contentType: file.type,
                    length: file.size,
                }, {
                    headers: {
                        "Authorization": `Bearer ${token}`
                    }
                }).then(() => {
                    queryClient.invalidateQueries(["archives"]);
                    queryClient.invalidateQueries(["folders"]);
                    queryClient.invalidateQueries(["groups"]);
                    toast.success("Arquivo enviado com sucesso!");

                    setFiles([]);

                    setIsUploading(false);
                }).catch((error) => {
                    setIsUploading(false);
                    console.error("Error uploading file:", error);
                })
            }).catch((error) => {
                setIsUploading(false);
                console.error("Error uploading file:", error);
            })
        } catch (error) {
            setIsUploading(false);
            console.error("Error uploading file:", error);
        }
    };

    return (
        <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
            <GroupToolbarProvider>
                <GroupToolbar/>
                <div className="flex flex-col items-end justify-center w-full pb-4 gap-2">
                    <FileUploader
                        value={files}
                        onValueChange={(value: File[] | null) => setFiles(value as File[])}
                        dropzoneOptions={dropZoneConfig}
                        className="flex flex-col p-[1px] items-center justify-center pb-1.5"
                    >
                        <FileInput className="outline-dashed outline-1 outline-white">
                            <div className="flex items-center justify-center flex-col pt-3 pb-4 w-full ">
                                <FilePlus className="h-8 w-8 stroke-current"/>
                                <span className="text-muted-foreground text-sm">Arraste e solte arquivos aqui</span>
                            </div>
                        </FileInput>
                        <FileUploaderContent>
                            {files &&
                                files.length > 0 &&
                                files.map((file, i) => (
                                    <FileUploaderItem key={i} index={i}>
                                        <Paperclip className="h-4 w-4 stroke-current"/>
                                        <span>{file.name}</span>
                                    </FileUploaderItem>
                                ))}
                        </FileUploaderContent>
                    </FileUploader>
                    <Button disabled={files?.length === 0 || isUploading} onClick={handleUpload} loading={isUploading}>
                        Enviar
                    </Button>
                </div>
                <GroupResources/>
            </GroupToolbarProvider>
        </div>
    );
}

Group.displayName = "Group";

export {Group};
