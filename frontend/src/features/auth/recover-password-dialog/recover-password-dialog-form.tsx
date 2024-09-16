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
import {usePostPasswordRecoveryLink} from "@/services/auth/use-post-password-recovery-link.ts";
import {Input} from "@/components/custom/input.tsx";
import {LucideMail} from "lucide-react";
import {DialogFooter} from "@/components/ui/dialog.tsx";
import {Button} from "@/components/custom/button.tsx";
import {useRecoverPasswordDialogContext} from "./recover-password-dialog-context.tsx";

const schema = z.object({
  email: z.string({required_error: "Email é obrigatório"})
    .email("Email inválido"),
})

function RecoverPasswordDialogForm() {
  const {close} = useRecoverPasswordDialogContext();
  const {mutateAsync, isLoading} = usePostPasswordRecoveryLink();

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      email: ''
    }
  });

  const submitForm = (values: z.infer<typeof schema>) => {
    mutateAsync(values).then(() => {
      close();
    });
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(submitForm)}>
        <div className='pb-6 space-y-4'>
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
        </div>
        <DialogFooter>
          <Button loading={isLoading} disabled={isLoading}>
            Confirmar
          </Button>
        </DialogFooter>
      </form>
    </Form>
  )
}

RecoverPasswordDialogForm.displayName = "RecoverPasswordDialogForm"

export {RecoverPasswordDialogForm};
