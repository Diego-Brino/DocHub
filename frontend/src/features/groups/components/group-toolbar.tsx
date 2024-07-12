import {Input} from "@/components/custom/input.tsx";
import {Search} from "lucide-react";

function GroupToolbar() {
  return(
    <div className='border-b p-4'>
      <Input
        className='w-64'
        placeholder='Filtrar...'
        endIcon={<Search/>}
      />
    </div>
  )
}

GroupToolbar.displayName = 'GroupToolbar'

export {GroupToolbar}