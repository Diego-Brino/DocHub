import {CardContent, CardFooter} from "@/components/ui/card.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Authentication} from "@/features/auth/types";
import {usePostAuthenticate} from "@/features/auth/hooks/use-post-authenticate.ts";
import {useRecoverPasswordDialogContext} from "@/features/auth/hooks/use-recover-password-dialog-context.ts";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from "@/components/ui/form.tsx";
import {Eye, EyeOff, LucideMail} from "lucide-react";
import {Input} from "@/components/custom/input.tsx";
import {useState} from "react";

const schema = z.object({
  email: z.string({required_error: "Email é obrigatório"})
    .email("Email inválido"),
  password: z.string({required_error: "Senha é obrigatória"})
    .min(8, "Senha Inválida"),
})

function LoginForm() {

  const {open} = useRecoverPasswordDialogContext();

  const {mutate, isLoading} = usePostAuthenticate();

  const [showPassword, setShowPassword] = useState(false);

  const onClickShowPassword = () => {
    setShowPassword(!showPassword)
  }

  const form = useForm<Authentication>({
    resolver: zodResolver(schema),
    defaultValues: {
      email: '',
      password: ''
    },
  })

  const onSubmit = (values: z.infer<typeof schema>) => {
    mutate(values);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <CardContent className='space-y-4'>
          <FormField
            control={form.control}
            name="email"
            render={({field}) => (
              <FormItem>
                <FormLabel>
                  Email
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    type='email'
                    error={form.formState.errors.email?.message}
                    endIcon={<LucideMail/>}
                  />
                </FormControl>
                <FormDescription/>
                <FormMessage/>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({field}) => (
              <FormItem>
                <FormLabel className='w-full h-[17px] inline-flex justify-between items-center'>
                  Senha
                  <Button
                    className='h-min p-0'
                    type='button'
                    variant='link'
                    onClick={open}
                  >
                    Esqueci a senha
                  </Button>
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.password?.message}
                    type={showPassword ? 'text' : 'password'}
                    endIcon={showPassword ? <Eye/> : <EyeOff/>}
                    onClickEndIcon={onClickShowPassword}
                  />
                </FormControl>
                <FormDescription/>
                <FormMessage/>
              </FormItem>
            )}
          />
        </CardContent>
        <CardFooter className='block space-y-2'>
          <Button type='submit' loading={isLoading} disabled={isLoading} className='w-full'>
            Entrar
          </Button>
        </CardFooter>
      </form>
    </Form>
)}

LoginForm.displayName = "LoginForm"

export {LoginForm}
