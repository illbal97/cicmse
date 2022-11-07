/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AsanaService } from './asana.service';

describe('Service: Asana', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AsanaService]
    });
  });

  it('should ...', inject([AsanaService], (service: AsanaService) => {
    expect(service).toBeTruthy();
  }));
});
