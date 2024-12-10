import { MutationCache, QueryCache, QueryClient } from "react-query";
import axios from "axios";
import { toast } from "sonner";

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
  queryCache: new QueryCache({
    onError: (error) => {
      if (!axios.isAxiosError(error)) {
        return;
      }

      if(error?.response?.data.message === 'Nenhuma entidade foi encontrada com o ID fornecido.'){
        return;
      }

      toast.error("Erro", {
        description: error?.response?.data.message || error.message,
      });
    },
  }),
  mutationCache: new MutationCache({
    onError: (error) => {
      if (!axios.isAxiosError(error)) {
        return;
      }

      if(error?.response?.data.message === 'Nenhuma entidade foi encontrada com o ID fornecido.'){
        return;
      }

      toast.error("Erro", {
        description: error?.response?.data.message || error.message,
      });
    },
  }),
});

export default queryClient;
