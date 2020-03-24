import {HttpParams} from '@angular/common/http';
import {FormControl} from '@angular/forms';
import {DateUtils} from '../issues/shared/utils/date-utils';
import { MatSort } from '@angular/material/sort';

enum SortOrderDirectionEnum {
  ASC = 'ASC',
  DESC = 'DESC'
}

/**
 * Obsługa parametrów http
 */
export class HttpParamsUtil {

  /**
   * Dodaje do parametrów http wartość z kontrolki formularza, jeżeli została ona ustawiona
   *
   * @param {HttpParams} httpParams - parametry http do wypełnienia
   * @param {FormControl} formControl - kontrolka formularza
   * @param {string} paramName - nazwa parametru
   * @returns {HttpParams} - parametry http
   */
  static addParam(httpParams: HttpParams, formControl: FormControl, paramName: string): HttpParams {
    if (formControl.value == null) {
      return httpParams;
    }
    return httpParams.append(paramName, formControl.value);
  }

  /**
   * Dodaje do parametrów http wartość daty z kontrolki formularza, jeżeli została ona ustawiona
   *
   * @param {HttpParams} httpParams - parametry http do wypełnienia
   * @param {FormControl} formControl - kontrolka formularza z datą
   * @param {string} paramName - nazwa parametru
   * @returns {HttpParams} - parametry http
   */
  static addDateParam(httpParams: HttpParams, formControl: FormControl, paramName: string): HttpParams {
    if (formControl.value == null) {
      return httpParams;
    }
    return DateUtils.prepareURLSearchParamFromMoment(paramName, formControl.value, httpParams);
  }

  /**
   * Dodaje do parametrów http sortowanie
   *
   * @param {MatSort} sort - komponent sortowania
   * @param {string} sortParamKey - nazwa parametru http do sortowania
   * @param {HttpParams} params - parametry http do uzupełnienia
   * @returns {HttpParams} - parametry http
   */
  static applySorting(sort: MatSort, sortParamKey: string, params: HttpParams): HttpParams {
    if (sort.active != null && sort.direction !== '') {
      const sortOrder = sort.direction === 'desc' ? '-' : '';
      return params.append(sortParamKey, `${sortOrder}${sort.active}`);
    } else {
      return params.delete(sortParamKey);
    }
  }


  static createSortingParamsObj(sort: MatSort): {sortField?: string, sortOrder?: string} {
    if (sort.active != null && sort.direction !== '') {
      const sortOrderDirection = sort.direction === 'desc' ? SortOrderDirectionEnum.ASC : SortOrderDirectionEnum.DESC;
      return {
        sortField: sort.active,
        sortOrder: sortOrderDirection
      };
    } else {
      return {};
    }
  }
}
