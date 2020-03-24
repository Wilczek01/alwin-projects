import {Pipe, PipeTransform} from '@angular/core';
import {IssueType} from '../../issues/shared/issue-type';

@Pipe({
  name: 'issueType'
})
export class IssueTypePipe implements PipeTransform {

  transform(type: IssueType, args: string[]): string {
    if (type == null) {
      return 'b/d';
    }

    if (type.name === 'PHONE_DEBT_COLLECTION_1') {
      return 'S1';
    }
    if (type.name === 'PHONE_DEBT_COLLECTION_2') {
      return 'S2';
    }

    return type.label;
  }

}
