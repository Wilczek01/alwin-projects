import {Component} from '@angular/core';

/**
 * Komponent z widokiem listy linków do wypowiedzeń
 */
@Component({
  selector: 'alwin-attachment-documents-dialog',
  styleUrls: ['./attachment-documents-dialog.component.css'],
  templateUrl: './attachment-documents-dialog.component.html'
})
export class AttachmentDocumentsDialogComponent {

  attachmentRefs: string[] = [];

}
