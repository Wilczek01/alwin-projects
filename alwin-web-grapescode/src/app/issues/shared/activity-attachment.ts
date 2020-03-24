import {Activity} from '../../activity/activity';

export class ActivityAttachment {
  activity: Activity;
  filename: string;
  uploadedInputStream: string;

  constructor(activity: Activity, filename: string, uploadedInputStream: string) {
    this.activity = activity;
    this.filename = filename;
    this.uploadedInputStream = uploadedInputStream;
  }
}
