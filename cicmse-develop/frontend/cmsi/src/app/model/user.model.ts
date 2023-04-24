import { Role } from "./role";

export class User {
  id: number|undefined;
  username: string = "";
  password: string = "";
  name: string = "";
  role: Role = Role.ADMIN;
  asanaPersonalAccessToken: String = "";
  gitlabPersonalAccessToken: String  = "";
  awsAccessKey: String = "";
  awsAccessSecretKey: String = ""
}
