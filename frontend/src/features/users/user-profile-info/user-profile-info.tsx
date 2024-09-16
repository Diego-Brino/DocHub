import {useGetUser} from "@/services/users/use-get-user.ts";
import {UserProfileInfoSkeleton} from "@/features/users/user-profile-info/user-profile-info-skeleton.tsx";
import {motion} from "framer-motion";
import {useState} from "react";
import {ChevronRight} from "lucide-react";
import {Button} from "@/components/custom/button.tsx";
import {Tooltip, TooltipContent, TooltipTrigger} from "@/components/ui/tooltip.tsx";
import {useUserProfileSheetContext} from "@/features/users/user-profile-sheet";

function UserProfileInfo() {
  const {data, isLoading} = useGetUser();

  const {isOpen, open} = useUserProfileSheetContext()

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
          <h2 className='text-sm overflow-ellipsis overflow-hidden text-nowrap'>{data.name}</h2>
          <p className='text-sm text-muted-foreground overflow-ellipsis overflow-hidden text-nowrap'>{data.email}</p>
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
    <UserProfileInfoSkeleton/>
  )
}

UserProfileInfo.displayName = 'UserProfileInfo'

export {UserProfileInfo}