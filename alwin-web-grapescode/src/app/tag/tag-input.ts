/**
 * Etykieta bez identyfikatora
 */
import {Tag} from './tag';
import {TagIcon} from './tag-icon';

export class TagInput {

  constructor(tag?: TagInput) {
    if (tag != null) {
      this.name = tag.name;
      this.color = tag.color;
      this.predefined = tag.predefined;
      this.type = tag.type;
      this.description = tag.description;
      this.tagIcon = tag.tagIcon;
    }
  }

  name: string;
  color: string;
  predefined: boolean;
  type: string;
  description: string;
  tagIcon: TagIcon;
}
