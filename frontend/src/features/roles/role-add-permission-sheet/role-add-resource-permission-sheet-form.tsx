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
import { useGetGroups } from "@/services/groups/use-get-groups.ts";
import { useGetResourcePermissions } from "@/services/resource-permissions/use-get-resource-permissions.ts";
import { usePostResourceRolePermissions } from "@/services/resource-role-permissions/use-post-resource-role-permissions.ts";
import { useGetGroupResources } from "@/services/groups/use-get-group-resources.ts";

const schemaResourcePermission = z.object({
  idRole: z.number(),
  group: z.string(),
  description: z.string(),
  idResource: z.string().nullable(),
});

function RoleAddResourcePermissionSheetForm() {
  const { selectedRoleId } = useRolePermissionsDialogContext();

  const { close } = useRoleAddPermissionSheetContext();

  const { data: dataResourcePermissions } = useGetResourcePermissions();
  const { data: dataGroups } = useGetGroups();

  const {
    mutateAsync: mutateAsyncPostResourceRolePermissions,
    isLoading: isLoadingPostRole,
  } = usePostResourceRolePermissions();

  const form = useForm<z.infer<typeof schemaResourcePermission>>({
    resolver: zodResolver(schemaResourcePermission),
    defaultValues: {
      idRole: selectedRoleId as number,
      group: "",
      description: "",
      idResource: null,
    },
  });

  const { data: dataResources } = useGetGroupResources(
    dataGroups?.find((group) => group.name === form.watch("group"))?.id || null,
  );

  const onSubmit = (values: z.infer<typeof schemaResourcePermission>) => {
    mutateAsyncPostResourceRolePermissions({
      idRole: values.idRole,
      idResourcePermission: dataResourcePermissions?.find(
        (resource) => resource.description === values.description,
      )?.id as number,
      idResource: Number(values.idResource),
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
            name="idResource"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Recurso</FormLabel>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    value={field.value?.toString()}
                  >
                    <SelectTrigger>
                      <SelectValue placeholder={"Selecione um recurso"} />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectGroup>
                        <SelectLabel>Recursos</SelectLabel>
                        {dataResources?.map((permission) => (
                          <SelectItem
                            key={permission.id}
                            value={permission.id.toString()}
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
                        {dataResourcePermissions?.map((permission) => (
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

RoleAddResourcePermissionSheetForm.displayName =
  "RoleAddResourcePermissionSheetForm";

export { RoleAddResourcePermissionSheetForm };
