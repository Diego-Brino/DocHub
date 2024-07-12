import {GroupList} from "@/features/groups/components/group-list.tsx";
import {GroupToolbar} from "@/features/groups/components/group-toolbar.tsx";

const GROUPS = [
  {
    avatarUrl: 'https://picsum.photos/250?a=1',
    title: 'Lorem ipsum 1',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=2',
    title: 'Lorem ipsum 2',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=3',
    title: 'Lorem ipsum 2',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=4',
    title: 'Lorem ipsum 3',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=5',
    title: 'Lorem ipsum 3',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=6',
    title: 'Lorem ipsum 4',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=7',
    title: 'Lorem ipsum 4',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=8',
    title: 'Lorem ipsum 4',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  },
  {
    avatarUrl: 'https://picsum.photos/250?a=9',
    title: 'Lorem ipsum 4',
    description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
  }
]

function GroupsPage() {
  return (
    <div className='flex flex-col w-full overflow-hidden'>
      <GroupToolbar/>
      <GroupList groups={GROUPS}/>
    </div>
  )
}

GroupsPage.displayName = "GroupsPage"

export { GroupsPage };
