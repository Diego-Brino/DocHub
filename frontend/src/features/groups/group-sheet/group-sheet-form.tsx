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
import { useGetGroup } from "@/services/groups/use-get-group.ts";
import { useGroupSheetContext } from "@/features/groups/group-sheet/group-sheet.tsx";
import { useEffect } from "react";
import { usePutGroup } from "@/services/groups/use-put-group.ts";
import { usePostGroup } from "@/services/groups/use-post-group.ts";

const groupSchema = z.object({
  id: z.number().nullish(),
  name: z
    .string({ required_error: "Nome é obrigatório" })
    .min(1, { message: "Nome deve ter no mínimo 1 caractere" })
    .max(256, { message: "Nome deve ter no máximo 256 caracteres" }),
  description: z
    .string({ required_error: "Descrição é obrigatória" })
    .max(512, { message: "Descrição deve ter no máximo 512 caracteres" }),
});

function GroupSheetForm() {
  const { selectedGroupId, close } = useGroupSheetContext();

  const { data } = useGetGroup(selectedGroupId);

  const { mutateAsync: mutateAsyncPutGroup, isLoading: isLoadingPutGroup } =
    usePutGroup();
  const { mutateAsync: mutateAsyncPostGroup, isLoading: isLoadingPostGroup } =
    usePostGroup();

  const form = useForm<z.infer<typeof groupSchema>>({
    resolver: zodResolver(groupSchema),
    defaultValues: {
      id: undefined,
      name: "",
      description: "",
    },
  });

  useEffect(() => {
    if (data) {
      form.reset({
        id: data.id,
        name: data.name,
        description: data.description,
      });
    }
  }, [data, form]);

  const onSubmit = (values: z.infer<typeof groupSchema>) => {
    if (selectedGroupId) {
      mutateAsyncPutGroup({
        groupId: values.id as number,
        group: {
          name: values.name,
          description: values.description,
        },
      }).then(() => {
        close();
      });
    } else {
      mutateAsyncPostGroup({
        name: values.name,
        description: values.description,
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
        </div>
        <SheetFooter className="flex items-center gap-4 flex-col">
          <Button
            type="submit"
            loading={isLoadingPutGroup || isLoadingPostGroup}
            disabled={
              isLoadingPutGroup || isLoadingPostGroup || !form.formState.isDirty
            }
          >
            Salvar
          </Button>
        </SheetFooter>
      </form>
    </Form>
  );
}

GroupSheetForm.displayName = "GroupSheetForm";

export { GroupSheetForm };
