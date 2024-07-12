import {ScrollArea} from "@/components/ui/scroll-area.tsx";

type GroupCardProps = {
  title: string,
  description: string,
  avatarUrl: string
}

function GroupCard({title, description, avatarUrl}: GroupCardProps) {
  return(
    <div className='flex flex-col gap-1 border rounded overflow-hidden min-w-32 w-64 h-96 transition ease-linear hover:cursor-pointer hover:scale-105 hover:-translate-y-2'>
      <img src={avatarUrl} className='h-64 w-64' alt='avatar'/>
      <div className='flex flex-col gap-1 p-2 flex-1 overflow-hidden'>
        <h1 className='text-xl'>{title}</h1>
        <ScrollArea>
          <p className='text-muted-foreground'>{description}</p>
        </ScrollArea>
      </div>
    </div>
  )
}

GroupCard.displayName = 'GroupCard'

export {GroupCard}