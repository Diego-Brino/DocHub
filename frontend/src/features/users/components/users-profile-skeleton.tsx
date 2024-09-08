import {Skeleton} from "@/components/ui/skeleton.tsx";

function UsersProfileSkeleton() {
  return (
    <div className='flex md:flex-1 gap-4 items-center justify-end'>
      <div className='flex gap-2'>
        <Skeleton className='rounded-full size-10'/>
        <div className='hidden md:flex flex-col gap-2'>
          <Skeleton className='w-40 h-4'/>
          <Skeleton className='w-40 h-4'/>
        </div>
      </div>
    </div>
  )
}

UsersProfileSkeleton.displayName = 'UsersProfileSkeleton'

export {UsersProfileSkeleton}