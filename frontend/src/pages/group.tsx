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
import { useState } from "react";
import { FilePlus, Paperclip } from "lucide-react";
import { usePresignedUrlMutation } from "@/services/archives/use-get-archive-presigned-url-create.ts";
import { useParams } from "react-router-dom";
import { Button } from "@/components/custom/button.tsx";
import axios from "axios";

function Group() {
  const [files, setFiles] = useState<File[] | null>(null);

  const { id } = useParams();

  console.log(id);

  const { mutateAsync } = usePresignedUrlMutation(Number(id));

  const dropZoneConfig = {
    maxFiles: 1,
    maxSize: 1024 * 1024 * 4,
    multiple: false,
  };

  const handleUpload = async () => {
    if (!files || files.length === 0) return;

    try {
      const { url } = await mutateAsync();
      const file = files[0];

      await axios.put(url, file, {
        headers: {
          "Content-Type": file.type,
        },
      });

      console.log("File uploaded successfully!");
    } catch (error) {
      console.error("Error uploading file:", error);
    }
  };

  return (
    <div className="flex flex-col w-full h-[calc(100vh_-_73px)] md:h-[calc(100vh_-_73px-2rem)]">
      <GroupToolbarProvider>
        <GroupToolbar />
        <div className="flex flex-col items-end justify-center w-full">
          <FileUploader
            value={files}
            onValueChange={setFiles}
            dropzoneOptions={dropZoneConfig}
            className="flex flex-col gap-4 p-2"
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
          <Button disabled={files?.length === 0} onClick={handleUpload}>
            Enviar
          </Button>
        </div>
        <GroupResources />
      </GroupToolbarProvider>
    </div>
  );
}

Group.displayName = "Group";

export { Group };
