import {Card, CardDescription, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {ResetPasswordCardForm} from "@/features/auth/reset-password-card/reset-password-card-form.tsx";

function ResetPasswordCard() {
  return (
    <Card className='w-full shadow-none md:w-[500px] border p-2'>
      <CardHeader>
        <CardTitle>
          Recuperar senha
        </CardTitle>
        <CardDescription>
          Insira sua nova senha.
        </CardDescription>
      </CardHeader>
      <ResetPasswordCardForm/>
    </Card>
  )
}

ResetPasswordCard.displayName = "ResetPasswordCard"

export {ResetPasswordCard};