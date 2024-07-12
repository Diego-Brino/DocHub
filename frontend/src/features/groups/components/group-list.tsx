import {Group} from "../types/types.ts";
import {GroupCard} from "./group-card.tsx";

type GroupListProps = {
  groups: Group[]
}

function GroupList({groups}: GroupListProps) {
  return(
    <div className='flex flex-wrap gap-8 p-8 overflow-scroll'>
      {groups.map((group, index) => (
        <GroupCard key={index} title={group.title} description={group.description} avatarUrl={group.avatarUrl}/>
      ))}
    </div>
  )
}

GroupList.displayName = 'GroupList'

export {GroupList}