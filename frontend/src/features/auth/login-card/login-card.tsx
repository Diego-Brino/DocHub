import logo from "@/assets/logo.svg";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card.tsx";
import { Separator } from "@/components/ui/separator.tsx";
import { ThemeButton } from "@/features/theme";
import { LoginCardForm } from "@/features/auth/login-card/login-card-form.tsx";
import {
  RecoverPasswordDialog,
  RecoverPasswordDialogProvider,
} from "@/features/auth/recover-password-dialog";

function LoginCard() {
  return (
    <>
      <div className="flex md:hidden gap-4 justify-center">
        <img src={logo} alt="logo" className="size-12" />
        <h1 className="text-5xl font-semibold">DocHub</h1>
      </div>
      <Card className="w-full shadow-none md:w-[700px] border p-2">
        <div className="flex gap-2 items-stretch">
          <div className="flex-1 hidden md:flex flex-col">
            <CardHeader>
              <CardTitle>Bem vindo(a) ao DocHub!</CardTitle>
              <CardDescription>
                Sua plataforma digital de gerenciamento de documentos.
              </CardDescription>
            </CardHeader>
            <CardContent className="h-full flex justify-center items-center">
              <img src={logo} alt="logo" className="size-40" />
            </CardContent>
          </div>
          <div className="py-6 hidden md:block">
            <Separator orientation="vertical" />
          </div>
          <div className="flex-1">
            <CardHeader>
              <CardTitle className="flex justify-between">
                Login
                <ThemeButton />
              </CardTitle>
            </CardHeader>
            <RecoverPasswordDialogProvider>
              <LoginCardForm />
              <RecoverPasswordDialog />
            </RecoverPasswordDialogProvider>
          </div>
        </div>
      </Card>
    </>
  );
}

export { LoginCard };
