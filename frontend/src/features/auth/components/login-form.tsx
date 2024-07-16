import {CardContent, CardFooter} from "@/components/ui/card.tsx";
import {Label} from "@/components/ui/label.tsx";
import {EmailInput, PasswordInput} from "@/features/auth";
import {InputValidation} from "@/components/custom/input-validation.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {Login} from "@/features/auth/types";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .email("Email inválido!"),
  password: z.string()
    .min(1, "Senha é obrigatória!"),
})

function LoginForm() {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false)

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<Login>({
    resolver: zodResolver(schema),
  })

  const onSubmit = (data: Login) => {
    console.log(data);
    setLoading(true);
    setTimeout(() => {
      setLoading(false);
      navigate('/home')
    }, 2000)
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
  )
}

LoginForm.displayName = "LoginForm"

export {LoginForm};
