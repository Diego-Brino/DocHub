import {
  GetRolesResponse,
  useGetRoles,
} from "@/services/roles/use-get-roles.ts";
import { RoleCard } from "@/features/roles";
import { AnimatePresence } from "framer-motion";
import { useRolesToolbarContext } from "@/features/roles/roles-toolbar/roles-toolbar.tsx";
import { RoleDeleteConfirmationAlertProvider } from "@/features/roles/role-delete-confirmation-alert/role-delete-confirmation-alert.tsx";

const filterRoles = (filter: string, roles: GetRolesResponse) => {
  return roles.filter((role) =>
    role.name.toLowerCase().includes(filter.toLowerCase()),
  );
};

function RolesList() {
  const { data, isLoading } = useGetRoles();
  const { appliedFilter } = useRolesToolbarContext();

  const filteredRoles = filterRoles(appliedFilter, data || []);

  return (
    <RoleDeleteConfirmationAlertProvider>
      <div className="w-full h-full gap-4 grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 overflow-y-scroll content-start relative mb-8 md:mb-0">
        {!isLoading &&
          data &&
          (filteredRoles.length > 0 ? (
            <AnimatePresence>
              {filteredRoles.map((role) => (
                <RoleCard key={role.id} role={role} />
              ))}
            </AnimatePresence>
          ) : (
            <div className="absolute flex justify-center items-center top-1/2 right-1/2 translate-x-1/2 -translate-y-1/2">
              <p className="text-muted-foreground">
                Nenhum registro encontrado.
              </p>
            </div>
          ))}
      </div>
    </RoleDeleteConfirmationAlertProvider>
  );
}

export { RolesList };
