import {Component, Input} from '@angular/core';
import {Activity} from './activity';
import {ActivityTypeConst} from './activity-type-const';
import {ActivityDetail} from './activity-detail';
import {ActivityUtils} from './activity.utils';

/**
 * Komponent odpowiedzialny za wyświetlanie załączników oraz formularza z uploadem plików
 */
@Component({
  selector: 'alwin-activity-attachments-inputs',
  styleUrls: ['./activity-attachments-inputs.component.css'],
  templateUrl: './activity-attachments-inputs.component.html'
})
export class ActivityAttachmentsInputsComponent {

  selectedFiles: FileList;

  @Input()
  activity: Activity;

  @Input()
  readOnly: boolean;

  @Input()
  filesData: Map<string, string>;

  isFieldVisitActivity(): boolean {
    return this.activity.activityType !== undefined && this.activity.activityType.key === ActivityTypeConst.FIELD_VISIT;
  }

  selectFile(event) {
    this.filesData.clear();
    this.selectedFiles = event.target.files;
    for (let i = 0; i < this.selectedFiles.length; i++) {

      const myReader = new FileReader();
      myReader.onloadend = (e) => {
        const s = myReader.result as string;
        const base64Content = s.substring(s.indexOf(',') + 1, s.length);
        this.filesData.set(this.selectedFiles[i].name, base64Content);
      };
      myReader.readAsDataURL(this.selectedFiles[i]);
    }
  }

  isKeyOfPhotoAttachment(property: ActivityDetail) {
    return ActivityUtils.isKeyOfPhotoAttachment(property);
  }
}
