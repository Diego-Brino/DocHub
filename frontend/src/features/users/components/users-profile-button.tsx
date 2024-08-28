import {useGetUser} from "@/features/users/hooks/use-get-user.ts";
import {UsersProfileButtonSkeleton} from "@/features/users/components/users-profile-button-skeleton.tsx";

function UsersProfileButton() {

  const {data, isLoading} = useGetUser();

  return !isLoading ? (
    <div className='flex md:flex-1 gap-4 items-center justify-end'>
      <div className='flex gap-2'>
        <img src={data?.avatarUrl} alt='avatar' className='rounded-full size-10'/>
        <div className='hidden md:flex flex-col'>
          <h2 className='text-sm'>{data?.name}</h2>
          <p className='text-sm text-muted-foreground'>{data?.email}</p>
        </div>
      </div>
    </div>
  ) : (
    <UsersProfileButtonSkeleton/>
  )
}

UsersProfileButton.displayName = 'UsersProfileButton'

export {UsersProfileButton}