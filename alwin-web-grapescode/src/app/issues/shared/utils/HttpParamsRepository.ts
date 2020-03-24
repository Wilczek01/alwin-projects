import {HttpParams} from '@angular/common/http';
import * as moment from 'moment';

type DefaultParamSupportedType = string | number;
type AddParamSupportedType = moment.Moment | string | number;
interface MergeSupportedType  {
    [name: string]: AddParamSupportedType;
}

export class HttpParamsRepository<T = string> {
    private params = new HttpParams();

    public addParam(paramName: T, value: AddParamSupportedType) {
        if (value) {
            if (moment.isMoment(value)) {
                this.addParamMoment(paramName, value as moment.Moment);
            } else {
                this.addParamString(paramName, value as string);
            }
        }
    }

    public delete(paramName: string, value?: string) {
        return this.params.delete(paramName as any, value);
    }

    public mergeParams(obj: MergeSupportedType) {
       Object.keys(obj).forEach((index) => {
        this.addParam(index as any, obj[index]);
       });
    }

    public getHttpParams() {
        return this.params;
    }

    private addParamString(paramName: T, value: string) {
        this.params = this.params.append(paramName as any, value);
    }

    private addParamMoment(paramName: T, value: moment.Moment) {
        if (value != null && value.isValid()) {
            this.params = this.params.append(paramName as any, value.format('YYYY-MM-DD'));
        }
    }
}
