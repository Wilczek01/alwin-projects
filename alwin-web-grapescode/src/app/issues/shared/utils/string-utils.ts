import {HttpParams} from '@angular/common/http';

export class StringUtils {
  static prepareURLSearchParamFromString(paramName: string, value: string, params: HttpParams): HttpParams {
    if (!this.isNullOrEmptyOrUndefined(value)) {
      return params.append(paramName, value);
    }

    return params;
  }

  static prepareURLSearchIdParamFromString(paramName: string, value: any, params: HttpParams): HttpParams {
    if (!this.isNullOrEmptyOrUndefined(value)) {
      return params.append(paramName, value.id);
    }

    return params;
  }

  static prepareURLSearchParamFromNumber(paramName: string, value: number, params: HttpParams): HttpParams {
    if (!this.isNullOrEmptyOrUndefined(value)) {
      return params.append(paramName, String(value));
    }

    return params;
  }

  static isNullOrEmptyOrUndefined(value) {
    return !value;
  }

  /**
   * Dodaje do przekazanych parametrów HTTP nowy parametr o podanej nazwie i wartości
   * Wartość zostaje pozbawiona myślników oraz spacji
   *
   * @param {string} paramName - nazwa parametru
   * @param {string} value - wartość parametru
   * @param {HttpParams} params - lista parametrów HTTP
   * @returns {HttpParams}lista parametrów HTTP
   */
  static prepareURLSearchParamFromExtractedString(paramName: string, value: string, params: HttpParams): HttpParams {
    if (this.isNullOrEmptyOrUndefined(value)) {
      return params;
    }
    value = value.split(' ').join('');
    value = value.split('-').join('');
    return params.append(paramName, value);
  }

  /**
   * Usuwa znaki nowej linii ze stringa
   * @param {string} value - string do usunięcia znaków nowej linii
   * @returns {string} - string bez nowych linii
   */
  static removeNewLines(value: string) {
    if (this.isNullOrEmptyOrUndefined(value)) {
      return value;
    }
    return value.replace(/(\r\n|\n|\r)/gm, '');
  }
}
