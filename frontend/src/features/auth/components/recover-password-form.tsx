import {z} from "zod";
import {usePostAuthenticate} from "@/features/auth/hooks/use-post-authenticate.ts";
import {useForm} from "react-hook-form";
import {Authentication} from "@/features/auth";
import {zodResolver} from "@hookform/resolvers/zod";

const schema = z.object({
  email: z.string()
    .min(1, "Email é obrigatório!")
    .min(3, "Email inválido!")
    .email("Email inválido!"),
})

function RecoverPasswordForm() {

  const {mutate, isLoading} = usePostAuthenticate();

  const {
    register,
    handleSubmit,
    formState: {errors}
  } = useForm<Authentication>({
    resolver: zodResolver(schema),
  })

}

RecoverPasswordForm.displayName = "RecoverPasswordForm"

export {RecoverPasswordForm};
