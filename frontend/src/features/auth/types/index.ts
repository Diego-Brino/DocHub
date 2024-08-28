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

type User = {
  id: number,
  name: string,
  email: string,
  username: string,
  avatarUrl: string
}

export type {
  Authentication,
  Token,
  TokenPayload,
  User
}