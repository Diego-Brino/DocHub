import { AnimatePresence } from "framer-motion";
import { GroupsGridCardSkeleton } from "@/features/groups/components/groups-grid-card-skeleton.tsx";
import {
  GetUsersResponse,
  useGetUsers,
} from "@/services/users/use-get-users.ts";
import { UserCard } from "@/features/users/user-card";
import { useUsersToolbarContext } from "@/features/users/users-toolbar/users-toolbar.tsx";
import {
  UserDeleteConfirmationAlert,
  UserDeleteConfirmationAlertProvider,
} from "@/features/users/user-delete-confirmation-alert/user-delete-confirmation-alert.tsx";

const filterUsers = (filter: string, users: GetUsersResponse[]) => {
  return users.filter((user) =>
    user.name.toLowerCase().includes(filter.toLowerCase()),
  );
};

function UsersList() {
  const { data, isLoading } = useGetUsers();
  const { appliedFilter } = useUsersToolbarContext();

  const filteredUsers = filterUsers(appliedFilter, data || []);

  return (
    <UserDeleteConfirmationAlertProvider>
      <div className="w-full h-full gap-4 grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 overflow-y-scroll content-start relative">
        {!isLoading && data ? (
          filteredUsers.length > 0 ? (
            <AnimatePresence>
              {filteredUsers.map((user) => (
                <UserCard key={user.id} user={user} />
              ))}
            </AnimatePresence>
          ) : (
            <div className="absolute flex justify-center items-center top-1/2 right-1/2 translate-x-1/2 -translate-y-1/2">
              <p className="text-muted-foreground">
                Nenhum registro encontrado.
              </p>
            </div>
          )
        ) : (
          <>
            {[1, 2, 3, 4, 5, 6].map((_group, index) => (
              <GroupsGridCardSkeleton key={index} />
            ))}
          </>
        )}
      </div>
      <UserDeleteConfirmationAlert />
    </UserDeleteConfirmationAlertProvider>
  );
}

export { UsersList };
