import { Button } from "@/components/custom/button.tsx";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form.tsx";
import { Input } from "@/components/custom/input.tsx";
import { SheetFooter } from "@/components/ui/sheet.tsx";
import { useGetRole } from "@/services/roles/use-get-role.ts";
import { useRoleSheetContext } from "@/features/roles/role-sheet/role-sheet.tsx";
import { ColorPicker } from "@/components/custom/color-picker.tsx";
import { useEffect } from "react";
import { usePutRole } from "@/services/roles/use-put-role.ts";
import { usePostRole } from "@/services/roles/use-post-role.ts";

const roleSchema = z.object({
  id: z.number().nullish(),
  name: z
    .string({ required_error: "Nome é obrigatório" })
    .min(1, { message: "Nome deve ter no mínimo 1 caractere" })
    .max(256, { message: "Nome deve ter no máximo 256 caracteres" }),
  description: z
    .string({ required_error: "Descrição é obrigatória" })
    .max(512, { message: "Descrição deve ter no máximo 512 caracteres" }),
  color: z
    .string({ required_error: "Cor é obrigatória" })
    .regex(/^#[0-9A-F]{6}$/i, { message: "Cor inválida" }),
  status: z.enum(["ATIVO", "INATIVO"]),
});

function RoleSheetForm() {
  const { selectedRoleId, close } = useRoleSheetContext();

  const { data } = useGetRole({
    id: selectedRoleId ? Number(selectedRoleId) : null,
  });

  const { mutateAsync: mutateAsyncPutRole, isLoading: isLoadingPutRole } =
    usePutRole();
  const { mutateAsync: mutateAsyncPostRole, isLoading: isLoadingPostRole } =
    usePostRole();

  const form = useForm<z.infer<typeof roleSchema>>({
    resolver: zodResolver(roleSchema),
    defaultValues: {
      id: undefined,
      name: "",
      description: "",
      color: "#000000",
      status: "ATIVO",
    },
  });

  useEffect(() => {
    if (data) {
      form.reset({
        id: data.id,
        name: data.name,
        description: data.description,
        color: data.color,
        status: data.status as "ATIVO" | "INATIVO",
      });
    }
  }, [data, form]);

  const onSubmit = (values: z.infer<typeof roleSchema>) => {
    if (selectedRoleId) {
      mutateAsyncPutRole({
        id: values.id as number,
        name: values.name,
        description: values.description,
        color: values.color,
        roleStatus: values.status === "ATIVO" ? "ACTIVE" : "INACTIVE",
      }).then(() => {
        close();
      });
    } else {
      mutateAsyncPostRole({
        name: values.name,
        description: values.description,
        color: values.color,
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
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="description"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Descrição</FormLabel>
                <FormControl>
                  <Input
                    {...field}
                    error={form.formState.errors.description?.message}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="color"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Cor</FormLabel>
                <FormControl>
                  <ColorPicker
                    className={"w-full"}
                    {...field}
                    value={field.value}
                    onChange={field.onChange}
                    color={field.value}
                  />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <SheetFooter className="flex items-center gap-4 flex-col">
          <Button
            type="submit"
            loading={isLoadingPutRole || isLoadingPostRole}
            disabled={
              isLoadingPutRole || isLoadingPostRole || !form.formState.isDirty
            }
          >
            Salvar
          </Button>
        </SheetFooter>
      </form>
    </Form>
  );
}

RoleSheetForm.displayName = "RoleSheetForm";

export { RoleSheetForm };
