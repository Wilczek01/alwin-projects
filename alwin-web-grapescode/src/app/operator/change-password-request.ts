/**
 * Żądanie zmiany hasła dla operatora
 */
export class ChangePasswordRequest {
  password: string;


  constructor(password?: string) {
    this.password = password;
  }
}
