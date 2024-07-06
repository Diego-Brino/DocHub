import AppRouter from "@/app/app-router.tsx";
import AppProvider from "@/app/app-provider.tsx";

function App() {
  return (
    <AppProvider>
      <AppRouter/>
    </AppProvider>
  )
}

export default App
