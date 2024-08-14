import {QueryCache, QueryClient} from "react-query";
import axios from "axios";
import {toast} from "sonner";
import {RotateCcw} from "lucide-react";
import {createElement} from "react";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false
    }
  },
  queryCache: new QueryCache({
    onError: ((error , query) => {
      if(!axios.isAxiosError(error)) {
        return;
      }

      toast.error('Erro', {
        description: error?.response?.data.message || error.message,
        classNames: {
          actionButton: '!inline-flex !items-center !justify-center !whitespace-nowrap !rounded-md !text-sm !font-medium !ring-offset-background !transition-colors !focus-visible:!outline-none !focus-visible:!ring-2 !focus-visible:!ring-ring !focus-visible:!ring-offset-2 !disabled:!pointer-events-none !disabled:!opacity-50 !border !border-input !bg-primary !hover:bg-primary/90 text-primary-foreground !h-10 !w-10'
        },
        action: {
          label: createElement(RotateCcw, { className: 'size-5' }),
          onClick: () => query.fetch()
        }
      })
    })
  })
});

export default queryClient;