import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'duration'
})
export class DurationPipe implements PipeTransform {

  transform(value: any, args: any[]): string {
    const from: Date = Number.isInteger(value) ? new Date(value) : value;
    const to: Date = (args.length > 0 && args[0]) ? Number.isInteger(value) ? new Date(args[0]) : args[0] : new Date();

    const diffSeconds = (to.getTime() - from.getTime()) / 1000;
    const hours = Math.floor(diffSeconds / 3600);
    const minutes = Math.floor((diffSeconds - (hours * 3600)) / 60);
    const seconds = Math.round(diffSeconds - (hours * 3600) - (minutes * 60));

    if (hours > 0) {
      return `${hours}h ${minutes}m ${seconds}s`;
    }
    if (minutes > 0) {
      return `${minutes}m ${seconds}s`;
    }
    return `${seconds}s`;
  }

}
