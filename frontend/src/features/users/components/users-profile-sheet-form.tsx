import {Sheet, SheetContent, SheetDescription, SheetFooter, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {useUsersProfileSheetContext} from "@/features/users/hooks/use-users-profile-sheet-context.ts";

function UsersProfileSheet() {
  const {isOpen, close} = useUsersProfileSheetContext()

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent>
        <SheetHeader>
          <SheetTitle>Perfil</SheetTitle>
          <SheetDescription>
            Edite suas informações relacionadas a sua conta.
          </SheetDescription>
        </SheetHeader>

        <SheetFooter>

        </SheetFooter>
      </SheetContent>
    </Sheet>
  )
}

UsersProfileSheet.displayName = 'UsersProfileSheet'

export {UsersProfileSheet}