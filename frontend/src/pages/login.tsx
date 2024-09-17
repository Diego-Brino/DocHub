import { LoginCard } from "@/features/auth/login-card/login-card.tsx";

function Login() {
  return (
    <div className="container h-screen flex flex-col gap-6 justify-center items-center">
      <LoginCard />
    </div>
  );
}

export { Login };
