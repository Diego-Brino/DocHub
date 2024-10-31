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
import { SheetFooter } from "@/components/ui/sheet.tsx";
import { useRoleAddPermissionSheetContext } from "@/features/roles/role-add-permission-sheet/role-add-permission-sheet.tsx";
import { usePostSystemRolePermissions } from "@/services/system-role-permissions/use-post-system-role-permissions.ts";
import { useGetSystemPermissions } from "@/services/system-permissions/use-get-system-permissions.ts";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select.tsx";
import { useRolePermissionsDialogContext } from "@/features/roles/role-permissions-dialog/role-permissions-dialog.tsx";
import { useGetRole } from "@/services/roles/use-get-role.ts";

const schemaSystemPermission = z.object({
  idRole: z.number(),
  description: z.string(),
});

function RoleAddSystemPermissionSheetForm() {
  const { selectedRoleId } = useRolePermissionsDialogContext();

  const { data: dataRole } = useGetRole({ id: selectedRoleId });

  const { close } = useRoleAddPermissionSheetContext();

  const { data } = useGetSystemPermissions();

  const {
    mutateAsync: mutateAsyncPostRoleSystemRolePermissions,
    isLoading: isLoadingPostRole,
  } = usePostSystemRolePermissions();

  const form = useForm<z.infer<typeof schemaSystemPermission>>({
    resolver: zodResolver(schemaSystemPermission),
    defaultValues: {
      idRole: selectedRoleId as number,
      description: "",
    },
  });

  const onSubmit = (values: z.infer<typeof schemaSystemPermission>) => {
    mutateAsyncPostRoleSystemRolePermissions({
      idRole: values.idRole,
      idSystemPermission: data?.find(
        (systemPermission) =>
          systemPermission.description === values.description,
      )?.id as number,
    }).then(() => {
      close();
    });
  };

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="grid gap-4 py-4">
        <div className="space-y-4">
          <FormField
            control={form.control}
            name="description"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Permissão</FormLabel>
                <FormControl>
                  <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder={"Selecione uma permissão"} />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectGroup>
                        <SelectLabel>Permissões</SelectLabel>
                        {data
                          ?.filter(
                            (role) =>
                              !dataRole?.systemPermissions.find(
                                (role2) => role2.id === role.id,
                              ),
                          )
                          .map((permission) => (
                            <SelectItem
                              key={permission.id}
                              value={permission.description}
                            >
                              {permission.description}
                            </SelectItem>
                          ))}
                      </SelectGroup>
                    </SelectContent>
                  </Select>
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
        </div>
        <SheetFooter className="flex items-center gap-4 flex-col">
          <Button
            type="submit"
            loading={isLoadingPostRole}
            disabled={isLoadingPostRole || !form.formState.isDirty}
          >
            Salvar
          </Button>
        </SheetFooter>
      </form>
    </Form>
  );
}

RoleAddSystemPermissionSheetForm.displayName =
  "RoleAddSystemPermissionSheetForm";

export { RoleAddSystemPermissionSheetForm };
