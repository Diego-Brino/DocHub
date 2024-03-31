import {useState} from "react";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import loginImg from "@/assets/login-img.svg";
import {Separator} from "@/components/ui/separator.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Input} from "@/components/custom/input.tsx";
import {InputValidation} from "@/components/custom/input-validation.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {TLogin} from "@/types/auth/login.ts";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .email("Email inválido!"),
  password: z.string()
    .min(1, "Senha é obrigatória!"),
})

function LoginPage() {

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<TLogin>({
    resolver: zodResolver(schema),
  })

  const [loading, setLoading] = useState(false)

  const onSubmit = (data: TLogin) => {
    console.log(data)
    setLoading(true)
    setTimeout(() => {
      setLoading(false)
    }, 2000)
  }

  return (
    <div className='container h-screen flex justify-center items-center'>
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
              <img src={loginImg} alt='Login image'/>
            </CardContent>
          </div>
          <div className='py-6 hidden md:block'>
            <Separator orientation='vertical'/>
          </div>
          <div className='flex-1'>
            <CardHeader>
              <CardTitle>
                Login
              </CardTitle>
            </CardHeader>
            <form onSubmit={handleSubmit(onSubmit)}>
              <CardContent className='space-y-4'>
                <div className='space-y-2'>
                  <Label htmlFor='email'>Email</Label>
                  <Input {...register('email')} id='email' type='text' error={errors?.email?.toString()}/>
                  <InputValidation error={errors?.email?.message?.toString()}/>
                </div>
                <div className='space-y-2'>
                  <div className="flex items-center justify-between gap-2">
                    <Label htmlFor="password">Password</Label>
                    <Button variant='link' asChild className='p-0 h-min underline text-black hover:text-primary'>
                      <a href='#'>Esqueceu a senha?</a>
                    </Button>
                  </div>
                  <Input {...register('password')} id='password' type='password' error={errors?.password?.toString()}/>
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

LoginPage.displayName = "LoginPage"

export {LoginPage}