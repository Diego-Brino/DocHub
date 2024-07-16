import React from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import App from "@/app/App.tsx";

async function setupMockWorker() {
  const {worker} = await import("@/mocks/browser.ts");
  return worker.start({
    onUnhandledRequest: "bypass"
  });
}

setupMockWorker().then(() => {
  ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
      <App/>
    </React.StrictMode>,
  )
})
