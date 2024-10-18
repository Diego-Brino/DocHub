import { Button } from "@/components/custom/button.tsx";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form.tsx";
import { Input } from "@/components/custom/input.tsx";
import { SheetFooter } from "@/components/ui/sheet.tsx";
import { useUserSheetContext } from "@/features/users/user-sheet/user-sheet.tsx";
import { useEffect } from "react";
import { useGetProfile } from "@/services/profiles/use-get-profile.ts";
import { usePutProfile } from "@/services/profiles/use-put-profile.ts";
import { usePostProfile } from "@/services/profiles/use-post-profile.ts";

const schema = z.object({
  name: z
    .string({ required_error: "Nome é obrigatório" })
    .min(1, { message: "Nome deve ter no mínimo 1 caractere" })
    .max(256, { message: "Nome deve ter no máximo 256 caracteres" }),
  username: z
    .string({ required_error: "Username is required" })
    .min(1, { message: "Usuário deve ter no mínimo 1 caractere" })
    .max(256, { message: "Usuário deve ter no máximo 256 caracteres" }),
  email: z
    .string({ required_error: "Email é obrigatório" })
    .email("Email inválido")
    .max(128, { message: "Email deve ter no máximo 128 caracteres" }),
});

function UserSheetForm() {
  const { selectedUserId, close } = useUserSheetContext();

  const { data } = useGetProfile({ id: selectedUserId });

  const { mutateAsync: mutateAsyncPutProfile, isLoading: isLoadingPutProfile } =
    usePutProfile();
  const {
    mutateAsync: mutateAsyncPostProfile,
    isLoading: isLoadingPostProfile,
  } = usePostProfile();

  const form = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
    defaultValues: {
      name: "",
      username: "",
      email: "",
    },
  });

  useEffect(() => {
    if (data) {
      form.reset({
        name: data.name,
        username: data.username,
        email: data.email,
      });
    }
  }, [data, form]);

  const onSubmit = (values: z.infer<typeof schema>) => {
    if (selectedUserId) {
      mutateAsyncPutProfile({
        id: selectedUserId,
        user: values,
      }).then(() => {
        close();
      });
    } else {
      mutateAsyncPostProfile({
        user: values,
      }).then(() => {
        close();
      });
    }
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="grid gap-4 py-4">
        <div className="space-y-4">
          <FormField
            control={form.control}
            name="name"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Nome</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.name?.message}
                  />
                </FormControl>
                <FormDescription />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Usuário</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.username?.message}
                  />
                </FormControl>
                <FormDescription />
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Email</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    type="email"
                    error={form.formState.errors.email?.message}
                  />
                </FormControl>
                <FormDescription />
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <SheetFooter className="flex justify-end items-center gap-4 flex-col">
          <Button
            type="submit"
            loading={isLoadingPostProfile || isLoadingPutProfile}
            disabled={
              isLoadingPostProfile ||
              isLoadingPutProfile ||
              !form.formState.isDirty
            }
          >
            Salvar
          </Button>
        </SheetFooter>
      </form>
    </Form>
  );
}

UserSheetForm.displayName = "UserSheetForm";

export { UserSheetForm };
