import { ResetPasswordCard } from "@/features/auth/reset-password-card/reset-password-card.tsx";

function ResetPassword() {
  return (
    <div className="container h-screen flex flex-col gap-6 justify-center items-center">
      <ResetPasswordCard />
    </div>
  );
}

ResetPassword.displayName = "ResetPassword";

export { ResetPassword };
