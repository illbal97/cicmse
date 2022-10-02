import { Role } from "./role";

export interface User {
    id: number,
    username: String,
    name: String,
    password: String,
    role: Role,
    refreshToken: String
    asanaPersonalAccessToken: String




}
