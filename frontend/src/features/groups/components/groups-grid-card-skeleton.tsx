import {Skeleton} from "@/components/ui/skeleton.tsx";

function GroupsGridCardSkeleton() {
  return(
    <div className='flex flex-col p-4 gap-4 rounded-lg bg-card border overflow-hidden min-w-32 w-full h-[30rem]'>
      <Skeleton className="h-64 w-full rounded-sm"/>
      <div className='flex flex-col gap-4 flex-1 overflow-hidden'>
        <Skeleton className="h-[26px] w-48 rounded-sm"/>
        <Skeleton className="flex-1 w-full rounded-sm"/>
        <div className='flex justify-end gap-2'>
          <Skeleton className="h-[40px] w-[40px] rounded-sm"/>
          <Skeleton className="h-[40px] w-[40px] rounded-sm"/>
          <Skeleton className="h-[40px] w-[116.2px] rounded-sm"/>
        </div>
      </div>
    </div>
  )
}

GroupsGridCardSkeleton.displayName = 'GroupsGridCardSkeleton'

export {GroupsGridCardSkeleton}