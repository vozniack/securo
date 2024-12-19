export interface AppState {
  theme: string;

  user: UserState;
}

export interface UserState {

  token?: string;
}
