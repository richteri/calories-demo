import { Role } from './role';

export class User {
  constructor(
    public id: number,
    public name: string,
    public calories: number,
    public username: string,
    public password: string,
    public role: Role = Role.USER
  ) {}

}
