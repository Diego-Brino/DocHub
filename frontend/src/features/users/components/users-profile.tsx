import {useGetUser} from "@/features/users/hooks/use-get-user.ts";
import {UsersProfileSkeleton} from "@/features/users/components/users-profile-skeleton.tsx";
import {motion} from "framer-motion";
import {useState} from "react";
import {ChevronRight} from "lucide-react";
import {Button} from "@/components/custom/button.tsx";
import {Tooltip, TooltipContent, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {useUsersProfileSheetContext} from "@/features/users/hooks/use-users-profile-sheet-context.ts";

function UsersProfile() {
  const {data, isLoading} = useGetUser();

  const {isOpen, open} = useUsersProfileSheetContext()

  const [isHovered, setIsHovered] = useState(false);

  return !isLoading && data ? (
    <div
      className='flex md:flex-1 items-center justify-end overflow-hidden'
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <motion.div
        initial={{x: 32}}
        animate={{x: isHovered || isOpen ? -12 : 32}}
        transition={{type: 'spring', stiffness: 260, damping: 20}}
        className='flex gap-2 overflow-hidden pl-8'
       >
        <img src={data.avatarUrl} alt='avatar' className='rounded-full size-10'/>
        <div className='hidden md:flex flex-col overflow-hidden'>
          <h2 className='text-sm overflow-ellipsis overflow-hidden'>{data.name}</h2>
          <p className='text-sm text-muted-foreground overflow-ellipsis overflow-hidden'>{data.email}</p>
        </div>
      </motion.div>
      <motion.div
        animate={{scale: isHovered || isOpen ? 1 : 0}}
        transition={{type: 'spring', stiffness: 260, damping: 20}}
      >
        <Tooltip>
          <TooltipTrigger asChild>
            <Button size='icon' variant='outline' className='w-8' onClick={open}>
              <ChevronRight className='size-5'/>
            </Button>
          </TooltipTrigger>
          <TooltipContent>
            Perfil
          </TooltipContent>
        </Tooltip>
      </motion.div>
    </div>
  ) : (
    <UsersProfileSkeleton/>
  )
}

UsersProfile.displayName = 'UsersProfile'

export {UsersProfile}