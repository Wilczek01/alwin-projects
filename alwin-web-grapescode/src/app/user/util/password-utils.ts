/**
 * Klasa pomocnicza do generowania nowego hasła
 */
export class PasswordUtils {

  private static CHARACTERS = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

  /**
   * Generuje nowe hasło
   */
  static generateNewPassword(id: number): string {
    let text = '';

    for (let i = 0; i < 5; i++) {
      text += this.CHARACTERS.charAt(Math.floor(Math.random() * this.CHARACTERS.length));
    }
    return text + id;
  }
}
