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
import { useGetGroupPermissions } from "@/services/group-permissions/use-get-group-permissions.ts";
import { useGetGroups } from "@/services/groups/use-get-groups.ts";
import { usePostGroupRolePermissions } from "@/services/group-role-permissions/use-post-group-role-permissions.ts";

const schemaGroupPermission = z.object({
  idRole: z.number(),
  group: z.string(),
  description: z.string(),
});

function RoleAddGroupPermissionSheetForm() {
  const { selectedRoleId } = useRolePermissionsDialogContext();

  const { close } = useRoleAddPermissionSheetContext();

  const { data: dataGroupPermissions } = useGetGroupPermissions();
  const { data: dataGroups } = useGetGroups();

  const {
    mutateAsync: mutateAsyncPostGroupRolePermissions,
    isLoading: isLoadingPostRole,
  } = usePostGroupRolePermissions();

  const form = useForm<z.infer<typeof schemaGroupPermission>>({
    resolver: zodResolver(schemaGroupPermission),
    defaultValues: {
      idRole: selectedRoleId as number,
      group: "",
      description: "",
    },
  });

  const onSubmit = (values: z.infer<typeof schemaGroupPermission>) => {
    mutateAsyncPostGroupRolePermissions({
      idRole: values.idRole,
      idGroupPermission: dataGroupPermissions?.find(
        (groupPermission) => groupPermission.description === values.description,
      )?.id as number,
      idGroup: dataGroups?.find((group) => group.name === values.group)
        ?.id as number,
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
            name="group"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Grupo</FormLabel>
                <FormControl>
                  <Select onValueChange={field.onChange} value={field.value}>
                    <SelectTrigger>
                      <SelectValue placeholder={"Selecione um grupo"} />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectGroup>
                        <SelectLabel>Grupos</SelectLabel>
                        {dataGroups?.map((group) => (
                          <SelectItem key={group.id} value={group.name}>
                            {group.name}
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
                        {dataGroupPermissions?.map((permission) => (
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

RoleAddGroupPermissionSheetForm.displayName = "RoleAddGroupPermissionSheetForm";

export { RoleAddGroupPermissionSheetForm };
