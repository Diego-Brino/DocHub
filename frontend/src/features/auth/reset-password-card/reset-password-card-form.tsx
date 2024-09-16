import {CardContent, CardFooter} from "@/components/ui/card.tsx";
import {Button} from "@/components/custom/button.tsx";
import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage
} from "@/components/ui/form.tsx";
import {Eye, EyeOff} from "lucide-react";
import {Input} from "@/components/custom/input.tsx";
import {useState} from "react";
import {useNavigate, useSearchParams} from "react-router-dom";
import {usePostPasswordRecoveryChange} from "@/services/auth/use-post-password-recovery-change.ts";

const schema = z.object({
  token: z.string({required_error: "Token é obrigatório"}),
  newPassword: z.string({required_error: "Senha é obrigatória"})
    .min(8, "Senha Inválida"),
  confirmNewPassword: z.string({required_error: "Senha é obrigatória"})
    .min(8, "Senha Inválida")
}).refine(
  (data) => data.newPassword === data.confirmNewPassword, {
    path: ["confirmNewPassword"],
    message: "Senhas não são iguais"
});

function ResetPasswordCardForm() {
  const navigate = useNavigate();

  const token = useSearchParams()[0].get('token');

  const {mutateAsync, isLoading} = usePostPasswordRecoveryChange();

  const [showPassword, setShowPassword] = useState(false);

  const onClickShowPassword = () => {
    setShowPassword(!showPassword)
  }

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      token: token || '',
      newPassword: '',
      confirmNewPassword: ''
    },
  })

  const onSubmit = (values: Omit<z.infer<typeof schema>, "confirmNewPassword">) => {
    mutateAsync(values).then(() => {
      navigate('/login', {replace: true});
    });
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <CardContent className='space-y-4'>
          <FormField
            control={form.control}
            name="newPassword"
            render={({field}) => (
              <FormItem>
                <FormLabel className='w-full h-[17px] inline-flex justify-between items-center'>
                  Senha
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.newPassword?.message}
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
          <FormField
            control={form.control}
            name="confirmNewPassword"
            render={({field}) => (
              <FormItem>
                <FormLabel>
                  Confirmar Senha
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.confirmNewPassword?.message}
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
        <CardFooter className='flex justify-end'>
          <Button type='submit' loading={isLoading} disabled={isLoading}>
            Confirmar
          </Button>
        </CardFooter>
      </form>
    </Form>
  )
}

ResetPasswordCardForm.displayName = "ResetPasswordCardForm"

export {ResetPasswordCardForm}
