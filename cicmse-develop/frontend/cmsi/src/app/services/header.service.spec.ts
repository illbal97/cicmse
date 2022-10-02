/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { HeaderService } from './base-header.service';

describe('Service: Header', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HeaderService]
    });
  });

  it('should ...', inject([HeaderService], (service: HeaderService) => {
    expect(service).toBeTruthy();
  }));
});
