import { MatPaginator } from '@angular/material/paginator';

export class PaginatorLabels {
  static addAlwinLabels(paginator: MatPaginator) {
    paginator._intl.nextPageLabel = 'Następna strona';
    paginator._intl.previousPageLabel = 'Poprzednia strona';
    paginator._intl.itemsPerPageLabel = 'Ilość na stronie:';
    paginator._intl.getRangeLabel = (page, pageSize, length) => {
      if (length === 0 || pageSize === 0) {
        return `0 z ${length}`;
      }
      length = Math.max(length, 0);
      const startIndex = page * pageSize;
      const endIndex = startIndex < length ?
        Math.min(startIndex + pageSize, length) :
        startIndex + pageSize;
      return `${startIndex + 1} - ${endIndex} z ${length}`;
    };
  }
}
