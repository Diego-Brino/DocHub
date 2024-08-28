type Authentication = {
  email: string
  password: string
}

type Token = {
  token: string
}

type TokenPayload = {
  id: number;
  iss: string;
  sub: string;
  iat: number;
  exp: number;
};


export type {
  Authentication,
  Token
  Token,
  TokenPayload,
}