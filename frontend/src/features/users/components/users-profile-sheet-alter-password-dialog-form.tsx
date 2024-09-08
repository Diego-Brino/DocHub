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
import {usePatchUserPassword} from "@/features/users/hooks/use-patch-user-password.ts";
import {
  useUsersProfileSheetAlterPasswordDialogContext
} from "@/features/users/hooks/use-users-profile-sheet-recover-password-dialog-context.ts";

const schema = z.object({
  oldPassword: z.string({required_error: "Senha é obrigatória"})
    .min(8, "Senha Inválida"),
  newPassword: z.string({required_error: "Nova senha é obrigatória"})
    .min(8, "Nova senha Inválida"),
  confirmNewPassword: z.string({required_error: "Confirmar nova senha é obrigatório"})
    .min(8, "Confirmar nova senha Inválido")
}).refine(
  (data) => data.newPassword === data.confirmNewPassword, {
    path: ["confirmNewPassword"],
    message: "Senhas não são iguais"
});

function UsersProfileSheetAlterPasswordDialogForm() {
  const {mutateAsync, isLoading} = usePatchUserPassword();

  const [showPassword, setShowPassword] = useState(false);

  const {close} = useUsersProfileSheetAlterPasswordDialogContext()

  const onClickShowPassword = () => {
    setShowPassword(!showPassword)
  }

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      oldPassword: '',
      newPassword: '',
      confirmNewPassword: ''
    },
  })

  const onSubmit = (values: Omit<z.infer<typeof schema>, "confirmNewPassword">) => {
    mutateAsync(values)
      .then(() => {
        form.reset(values)
        close();
      });
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <CardContent className='space-y-4'>
          <FormField
            control={form.control}
            name="oldPassword"
            render={({field}) => (
              <FormItem>
                <FormLabel className='w-full h-[17px] inline-flex justify-between items-center'>
                  Senha atual
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.oldPassword?.message}
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
            name="newPassword"
            render={({field}) => (
              <FormItem>
                <FormLabel className='w-full h-[17px] inline-flex justify-between items-center'>
                  Nova senha
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
                  Confirmar nova senha
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

UsersProfileSheetAlterPasswordDialogForm.displayName = "UsersProfileSheetAlterPasswordDialogForm"

export {UsersProfileSheetAlterPasswordDialogForm}
