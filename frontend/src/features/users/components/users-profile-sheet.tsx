import {Sheet, SheetContent, SheetDescription, SheetHeader, SheetTitle} from "@/components/ui/sheet.tsx";
import {useUsersProfileSheetContext} from "@/features/users/hooks/use-users-profile-sheet-context.ts";
import {UsersProfileSheetForm} from "@/features/users/components/users-profile-sheet-form.tsx";
import {UsersProfileSheetAvatar} from "@/features/users/components/users-profile-sheet-avatar.tsx";

function UsersProfileSheet() {
  const {isOpen, close} = useUsersProfileSheetContext()

  return (
    <Sheet open={isOpen} onOpenChange={(open) => !open && close()}>
      <SheetContent onOpenAutoFocus={(e) => e.preventDefault()}>
        <SheetHeader>
          <SheetTitle>Perfil</SheetTitle>
          <SheetDescription>
            Edite suas informações relacionadas a sua conta.
          </SheetDescription>
        </SheetHeader>
        <UsersProfileSheetAvatar/>
        <UsersProfileSheetForm/>
      </SheetContent>
    </Sheet>
  )
}

UsersProfileSheet.displayName = 'UsersProfileSheet'

export {UsersProfileSheet}