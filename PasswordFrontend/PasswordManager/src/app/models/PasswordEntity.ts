export class PasswordEntity {
  public endPassword: string;
  public name: string;
  public url: string;
  public view = false;

  constructor(endPassword: string, name: string, url: string) {
    this.endPassword = endPassword;
    this.name = name;
    this.url = url;
  }
}
