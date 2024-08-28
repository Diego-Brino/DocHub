import {TokenPayload} from "@/features/auth/types";

function extractTokenPayload(token: string): TokenPayload {
  const encodedTokenPayload = token.split('.')[1];
  const decodedTokenPayload = window.atob(encodedTokenPayload);
  return JSON.parse(decodedTokenPayload);
}

export {
  extractTokenPayload
}