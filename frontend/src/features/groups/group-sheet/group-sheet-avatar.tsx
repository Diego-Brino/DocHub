import React from "react";
import { Label } from "@/components/ui/label.tsx";
import { UploadIcon } from "lucide-react";
import { usePatchGroupAvatar } from "@/services/groups/use-patch-group-avatar.ts";
import { useGetGroup } from "@/services/groups/use-get-group.ts";

function GroupSheetAvatar({ id }: { id: number | null }) {
  const { mutate } = usePatchGroupAvatar();
  const { data } = useGetGroup(id);

  const handleAvatarChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (!file) {
      return;
    }

    mutate({ avatar: file, id: id as number });
  };

  return (
    <div className="flex flex-col items-center space-y-4 pt-4">
      <Label htmlFor="avatar" className="relative cursor-pointer group">
        <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-full opacity-0 group-hover:opacity-100 transition-opacity">
          <UploadIcon className="text-white w-8 h-8" />
        </div>
        <img
          src={`${data?.groupUrl}`}
          alt="Avatar"
          className="w-32 h-32 rounded-full object-cover"
        />
      </Label>
      <input
        id="avatar"
        type="file"
        accept="image/*"
        className="hidden"
        onChange={handleAvatarChange}
      />
    </div>
  );
}

GroupSheetAvatar.displayName = "GroupSheetAvatar";

export { GroupSheetAvatar };
