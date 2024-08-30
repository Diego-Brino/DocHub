import React, {useEffect, useState} from "react";
import {Label} from "@/components/ui/label.tsx";
import {Skeleton} from "@/components/ui/skeleton.tsx";
import {UploadIcon} from "lucide-react";
import {useGetUserAvatar} from "@/features/users/hooks/use-get-user-avatar.ts";

function UsersProfileSheetAvatar() {
  const {data, isLoading} = useGetUserAvatar();
  const [avatar, setAvatar] = useState<File | null>(null);
  const [avatarUrl, setAvatarUrl] = useState<string | null>(null);

  const handleAvatarChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      const newAvatar = event.target.files[0];
      setAvatar(newAvatar);

      if (avatarUrl) {
        URL.revokeObjectURL(avatarUrl);
      }
      
      const newAvatarUrl = URL.createObjectURL(newAvatar);
      setAvatarUrl(newAvatarUrl);
    }
  };

  useEffect(() => {
    if (!isLoading && data) {
      setAvatar(data);
      
      if (avatarUrl) {
        URL.revokeObjectURL(avatarUrl);
      }
      
      const newAvatarUrl = URL.createObjectURL(data);
      setAvatarUrl(newAvatarUrl);
    }
  }, [data, isLoading]);

  useEffect(() => {
    return () => {
      if (avatarUrl) {
        URL.revokeObjectURL(avatarUrl);
      }
    };
  }, [avatarUrl]);

  return (
    <div className="flex flex-col items-center space-y-4 pt-4">
      <Label htmlFor="avatar" className="relative cursor-pointer group">
        <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-full opacity-0 group-hover:opacity-100 transition-opacity">
          <UploadIcon className="text-white w-8 h-8"/>
        </div>
        {avatar ? (
          <img
            src={avatarUrl as string}
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