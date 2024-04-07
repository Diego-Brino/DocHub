import {useState} from "react";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import logo from "@/assets/logo.svg";
import logoDark from "@/assets/logo-dark.svg";
import {Separator} from "@/components/ui/separator.tsx";
import {Label} from "@/components/ui/label.tsx";
import {InputValidation} from "@/components/custom/input-validation.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {Login} from "@/types/login.ts";
import {PasswordInput} from "@/pages/login/components/password-input.tsx";
import {EmailInput} from "@/pages/login/components/email-input.tsx";
import {DarkModeButton} from "@/components/common/dark-mode-button.tsx";
import {useDarkModeContext} from "@/hooks/use-dark-mode-context.ts";
import {useNavigate} from "react-router-dom";
import {withPageTransition} from "@/hocs/with-page-transition.tsx";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .email("Email inválido!"),
  password: z.string()
    .min(1, "Senha é obrigatória!"),
})

function LoginPage() {

  const {darkMode} = useDarkModeContext();

  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<Login>({
    resolver: zodResolver(schema),
  })

  const [loading, setLoading] = useState(false)

  const onSubmit = (data: Login) => {
    console.log(data);
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      navigate('/home')
    }, 2000)
  }

  return (
    <div className='container h-screen flex flex-col gap-6 justify-center items-center'>
      <div className='flex md:hidden gap-4 justify-center'>
        <img src={darkMode ? logoDark : logo} alt='logo' className='size-12'/>
        <h1 className='text-5xl font-semibold'>DocHub</h1>
      </div>
      <Card className='w-full shadow-none md:w-[700px] border p-2'>
        <div className='flex gap-2 items-stretch'>
          <div className='flex-1 hidden md:flex flex-col'>
            <CardHeader>
              <CardTitle>
                Bem vindo(a) ao DocHub!
              </CardTitle>
              <CardDescription>
                Sua plataforma digital de gerenciamento de documentos.
              </CardDescription>
            </CardHeader>
            <CardContent className='h-full flex justify-center items-center'>
              <img src={darkMode ? logoDark : logo} alt='logo' className='size-40'/>
            </CardContent>
          </div>
          <div className='py-6 hidden md:block'>
          <Separator orientation='vertical'/>
          </div>
          <div className='flex-1'>
            <CardHeader>
              <CardTitle>
                <div className='flex justify-between items-center'>
                  Login
                  <DarkModeButton/>
                </div>
              </CardTitle>
            </CardHeader>
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
                    <Button variant='link' asChild className='p-0 h-min underline text-current hover:text-primary'>
                      <a href='#'>Esqueceu a senha?</a>
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
                <Button type='submit' loading={loading} disabled={loading} className='w-full'>
                  Entrar
                </Button>
              </CardFooter>
            </form>
          </div>
        </div>
      </Card>
    </div>
  )
}

const LoginPageWithTransition = withPageTransition(LoginPage);

export { LoginPageWithTransition as LoginPage };