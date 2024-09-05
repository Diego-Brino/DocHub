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
import {Input} from "@/components/custom/input.tsx";
import {SheetFooter} from "@/components/ui/sheet.tsx";
import {useGetUser} from "@/features/users/hooks/use-get-user.ts";
import {usePutUser} from "@/features/users/hooks/use-put-user.ts";

const schema = z.object({
  name: z
    .string({required_error: "Nome é obrigatório"})
    .min(1, {message: "Nome deve ter no mínimo 1 caractere"})
    .max(256, {message: "Nome deve ter no máximo 256 caracteres"}),
  username: z
    .string({required_error: "Username is required"})
    .min(1, {message: "Usuário deve ter no mínimo 1 caractere"})
    .max(256, {message: "Usuário deve ter no máximo 256 caracteres"}),
  email: z
    .string({required_error: "Email é obrigatório"})
    .email("Email inválido")
    .max(128, {message: "Email deve ter no máximo 128 caracteres"}),
})

function UsersProfileSheetForm() {
  const { data, isLoading: isGetUserLoading } = useGetUser();
  const { mutate, isLoading: isPutUserLoading } = usePutUser();

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      name: data?.name || '',
      email: data?.email || '',
      username: data?.username || '',
    },
  })

  const onSubmit = (values: z.infer<typeof schema>) => {
    mutate(values)
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className='grid gap-4 py-4'>
        <div className='space-y-4'>
          <FormField
            control={form.control}
            name="name"
            render={({field}) => (
              <FormItem>
                <FormLabel>
                  Nome
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.name?.message}
                  />
                </FormControl>
                <FormDescription/>
                <FormMessage/>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="username"
            render={({field}) => (
              <FormItem>
                <FormLabel>
                  Usuário
                </FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.username?.message}
                  />
                </FormControl>
                <FormDescription/>
                <FormMessage/>
              </FormItem>
            )}
          />
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
                  />
                </FormControl>
                <FormDescription/>
                <FormMessage/>
              </FormItem>
            )}
          />
        </div>
        <SheetFooter className='flex justify-end'>
          <Button type='submit' loading={isGetUserLoading || isPutUserLoading} disabled={isGetUserLoading || isPutUserLoading}>
            Salvar
          </Button>
        </SheetFooter>
      </form>
    </Form>
  )
}

UsersProfileSheetForm.displayName = "UsersProfileSheetForm"

export {UsersProfileSheetForm}
