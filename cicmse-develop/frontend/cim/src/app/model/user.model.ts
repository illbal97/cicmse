import { Role } from "./role";

export class User {
  id: number = -1;
  username: string = "";
  name: string = "";
  role: Role = Role.ADMIN;
  asanaPersonalAccessToken: String = "";
  gitlabPersonalAccessToken: String  = "";
  awsAccessKey: String = "";
  awsAccessSecretKey: String = ""
}
