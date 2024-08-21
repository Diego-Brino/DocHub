import {CardContent, CardFooter} from "@/components/ui/card.tsx";
import {Label} from "@/components/ui/label.tsx";
import {EmailInput, PasswordInput} from "@/features/auth";
import {InputValidation} from "@/components/custom/input-validation.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Authentication} from "@/features/auth/types";
import {usePostAuthenticate} from "@/features/auth/hooks/use-post-authenticate.ts";
import {useRecoverPasswordDialogContext} from "@/features/auth/hooks/use-recover-password-dialog-context.ts";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .min(3, "Email inválido!")
    .email("Email inválido!"),
  password: z.string()
})

function LoginForm() {

  const {open} = useRecoverPasswordDialogContext();

  const {mutate, isLoading} = usePostAuthenticate();

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<Authentication>({
    resolver: zodResolver(schema),
  })

  const onSubmit = (data: Authentication) => {
    mutate(data);
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <CardContent className='space-y-4'>
        <div className='space-y-2'>
          <Label htmlFor='email'>Email</Label>
          <EmailInput
            {...register('email')}
            id='email'
            error={errors?.email?.toString()}
          />
          <InputValidation error={errors?.email?.message?.toString()}/>
        </div>
        <div className='space-y-2'>
          <div className="flex items-center justify-between gap-2">
            <Label htmlFor="password">Senha</Label>
            <Button
              className='p-0 h-min underline text-current hover:text-primary'
              variant='link'
              onClick={open}
              type='button'
            >
              Esqueci minha senha
            </Button>
          </div>
          <PasswordInput
            {...register('password')}
            id='password'
            error={errors?.password?.toString()}
          />
          <InputValidation error={errors?.password?.message?.toString()}/>
        </div>
      </CardContent>
      <CardFooter className='block space-y-2'>
        <Button type='submit' loading={isLoading} disabled={isLoading} className='w-full'>
          Entrar
        </Button>
      </CardFooter>
    </form>
  )
}

LoginForm.displayName = "LoginForm"

export {LoginForm};
