export class TagIcon {

  constructor(tagIcon?: TagIcon) {
    if (tagIcon != null) {
      this.id = tagIcon.id;
      this.name = tagIcon.name;
    }
  }

  id: number;
  name: string;
}
