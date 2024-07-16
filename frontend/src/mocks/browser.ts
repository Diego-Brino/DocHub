import {setupWorker} from "msw/browser";
import {handlers} from "@/mocks/handlers.ts";

const worker = setupWorker(...handlers);

export {worker}