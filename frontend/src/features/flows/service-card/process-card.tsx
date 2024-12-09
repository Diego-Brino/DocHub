import { ArrowRight } from "lucide-react";
import { Button } from "@/components/custom/button.tsx";
import { Card } from "@/components/ui/card.tsx";
import { useNavigate, useParams } from "react-router-dom";
import { Process } from "@/pages/flow.tsx";
import { Separator } from "@/components/ui/separator.tsx";

function ProcessCard({ process, order }: { process: Process; order: number }) {
  const navigate = useNavigate();

  const { id, flowId } = useParams();

  return (
    <>
      <Card key={process.id} className={"w-full p-4"}>
        <div className="flex gap-4 items-center justify-between">
          <div className="flex gap-2 items-center justify-start">
            <h2 className="text-lg font-semibold">{"Processo - " + order}</h2>
            <Separator orientation="vertical" />
            <p className="text-sm text-muted-foreground">
              Criado em: {process.startDate.split(" ")[0]}
            </p>
            {process.endDate && (
              <p className="text-sm text-muted-foreground">
                Finalizado em: {process.endDate.split(" ")[0]}
              </p>
            )}
          </div>
          <div className="flex gap-4 items-center">
            <Button
              className="gap-2"
              onClick={() => {
                navigate(
                  `/groups/${id}/flows/${flowId}/processes/${process.id}`,
                );
              }}
            >
              Acessar
              <ArrowRight />
            </Button>
          </div>
        </div>
      </Card>
    </>
  );
}

export { ProcessCard };
