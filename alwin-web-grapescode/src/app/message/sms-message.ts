export class SmsMessage {
  issueId: number;
  message: String;
  phoneNumber: String;

  constructor(issueId: number, message: String, phoneNumber: String) {
    this.issueId = issueId;
    this.message = message;
    this.phoneNumber = phoneNumber;
  }
}
