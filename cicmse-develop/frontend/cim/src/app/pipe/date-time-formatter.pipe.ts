import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateTimeFormatter'
})
export class DateTimeFormatterPipe implements PipeTransform {

  transform(value: Date, ...args: unknown[]): String {
    const day = value.getDate() < 10? "0"+ value.getDate(): value.getDate();

    return value.getFullYear() + "-" + (value.getMonth() + 1) + "-" + day;

  }

}
