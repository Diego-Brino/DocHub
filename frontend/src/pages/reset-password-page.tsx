import {ResetPasswordCard} from "@/features/auth/components/reset-password-card.tsx";

function ResetPasswordPage() {
  return (
    <div className='container h-screen flex flex-col gap-6 justify-center items-center'>
      <ResetPasswordCard/>
    </div>
  )
}

ResetPasswordPage.displayName = "ResetPasswordPage"

export {ResetPasswordPage};