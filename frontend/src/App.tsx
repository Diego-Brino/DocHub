import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Input} from "@/components/custom/input.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod"
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Separator} from "@/components/ui/separator.tsx";
import loginImg from "./assets/login-img.svg";
import {useState} from "react";
import {InputValidation} from "@/components/custom/input-validation.tsx";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .email("Email inválido"),
  password: z.string()
    .min(1, "Senha é obrigatória!"),
})

function App() {

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm({
    resolver: zodResolver(schema)
  })

  const [loading, setLoading] = useState(false)

  const onSubmit = (data: any) => {
    setLoading(true)
    setTimeout(() => {
      setLoading(false)
    }, 2000)
  }

  return (
    <div className='container h-screen flex justify-center items-center'>
      <Card className='w-[680px]'>
        <div className='flex gap-2 items-stretch'>
          <div className='flex-1 flex flex-col'>
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
          <div className='py-6'>
            <Separator orientation='vertical'/>
          </div>
          <div className='flex-1'>
            <CardHeader>
              <CardTitle>
                Login
              </CardTitle>
            </CardHeader>
            <form onSubmit={handleSubmit(onSubmit)}>
              <CardContent className='space-y-2'>
                <div className='space-y-2'>
                  <Label htmlFor='email'>Email</Label>
                  <Input {...register('email')} id='email' type='email' error={errors?.email?.toString()}/>
                  <InputValidation error={errors?.email?.message?.toString()}/>
                </div>
                <div className='space-y-2'>
                  <Label htmlFor='password'>Senha</Label>
                  <Input {...register('password')} id='password' type='password' error={errors?.password?.toString()}/>
                  <InputValidation error={errors?.password?.message?.toString()}/>
                </div>
              </CardContent>
              <CardFooter className='flex justify-between'>
                <Button variant='link' asChild>
                  <a href='#'>Esqueci a senha</a>
                </Button>
                <Button type='submit' loading={loading} disabled={loading}>
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

export default App
