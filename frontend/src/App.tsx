import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {Input} from "@/components/ui/input.tsx";
import {Label} from "@/components/ui/label.tsx";
import {Button} from "@/components/ui/button.tsx";
import {z} from "zod"
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Separator} from "@/components/ui/separator.tsx";
import loginImg from "./assets/login-img.svg";

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

  const onSubmit = (data: any) => {
    console.log(data)
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
                  <Input {...register('email')} id='email' type='email' className={errors?.email && 'border-red-500'}/>
                  {errors?.email && <p className='text-xs text-red-500'>{errors?.email.message?.toString()}</p>}
                </div>
                <div className='space-y-2'>
                  <Label htmlFor='password'>Senha</Label>
                  <Input {...register('password')} id='password' type='password'
                         className={errors?.password && 'border-red-500'}/>
                  {errors?.password && <p className='text-xs text-red-500'>{errors?.password.message?.toString()}</p>}
                </div>
              </CardContent>
              <CardFooter className='flex justify-between'>
                <a href='#' className='text-sm'>Esqueci a senha</a>
                <Button>
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
