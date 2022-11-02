import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateTime'
})
export class DateTimePipe implements PipeTransform {

  transform(value: number, ...args: unknown[]): any {
    const date = new Date(value);
    return date;

  }

}
