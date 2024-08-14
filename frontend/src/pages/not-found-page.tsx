import {Undo2} from "lucide-react";
import {Button} from "@/components/custom/button.tsx";
import {useGoToPreviousRoute} from "@/features/not-found";

function NotFoundPage() {
  const goToPreviousRoute = useGoToPreviousRoute();

  return (
    <div className='absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 size-72 rounded-full p-12 flex justify-center items-center flex-col gap-4'>
      <h1 className='text-8xl font-roboto-mono'>404</h1>
      <p className='text-center'>Página não encontrada</p>
      <Button onClick={goToPreviousRoute} className='flex justify-center items-center'>
        <Undo2 className='mr-2 size-4'/>
        <span>Voltar</span>
      </Button>
    </div>
  )
}

NotFoundPage.displayName = "NotFoundPage"

export { NotFoundPage };
