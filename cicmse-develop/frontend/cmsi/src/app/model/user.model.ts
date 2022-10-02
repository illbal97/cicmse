import { Role } from "./role";

export class User {
  id: number|undefined;
  username: string = "";
  password: string = "";
  name: string = "";
  accessToken: string = "";
  refreshToken: string = "";
  role: Role = Role.ADMIN;
  asanaPersonalAccessToken: String | null = "";
}
