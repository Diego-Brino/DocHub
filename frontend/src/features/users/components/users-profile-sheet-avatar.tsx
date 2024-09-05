import React from "react";
import {Label} from "@/components/ui/label.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {UploadIcon} from "lucide-react";
import {useGetUser} from "@/features/users/hooks/use-get-user.ts";
import {usePatchUserAvatar} from "@/features/users/hooks/use-patch-user-avatar.ts";

function UsersProfileSheetAvatar() {
  const {data, isLoading} = useGetUser();
  const {mutate} = usePatchUserAvatar();

  const handleAvatarChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];

    if (!file){
      return;
    }

    mutate(file);
  };

  return (
    <div className="flex flex-col items-center space-y-4 pt-4">
      <Label htmlFor="avatar" className="relative cursor-pointer group">
        <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-full opacity-0 group-hover:opacity-100 transition-opacity">
          <UploadIcon className="text-white w-8 h-8"/>
        </div>
        {!isLoading && data ? (
          <img
            src={`${data.avatarUrl}`}
            alt="Avatar"
            className="w-32 h-32 rounded-full object-cover"
          />
        ) : (
          <Skeleton className="w-32 h-32 rounded-full"/>
        )}
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

UsersProfileSheetAvatar.displayName = 'UsersProfileSheetAvatar';

export { UsersProfileSheetAvatar };