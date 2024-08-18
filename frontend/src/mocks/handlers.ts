import {delay, http, HttpResponse} from "msw";

const handlers = [
  http.get('groups', async () => {
    await delay(1000)
    return HttpResponse.json([
      {
        id: 1,
        avatarUrl: 'https://picsum.photos/600?a=1',
        title: 'Lorem ipsum 1',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 2,
        avatarUrl: 'https://picsum.photos/600?a=2',
        title: 'Lorem ipsum 2',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 3,
        avatarUrl: 'https://picsum.photos/600?a=3',
        title: 'Lorem ipsum 2',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 4,
        avatarUrl: 'https://picsum.photos/600?a=4',
        title: 'Lorem ipsum 3',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 5,
        avatarUrl: 'https://picsum.photos/600?a=5',
        title: 'Lorem ipsum 3',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 6,
        avatarUrl: 'https://picsum.photos/600?a=6',
        title: 'Lorem ipsum 4',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 7,
        avatarUrl: 'https://picsum.photos/600?a=7',
        title: 'Lorem ipsum 4',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 8,
        avatarUrl: 'https://picsum.photos/600?a=8',
        title: 'Lorem ipsum 4',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      },
      {
        id: 9,
        avatarUrl: 'https://picsum.photos/600?a=9',
        title: 'Lorem ipsum 4',
        description: 'Lorem ipsum dolor sit amet, consectetur adipisicing elit. A aliquid animi aperiam aspernatur commodi dicta, eius error, fugiat inventore iure magni necessitatibus quae sapiente, sequi sunt tempore tenetur unde vero'
      }
    ]);
  }),
];

export {handlers};