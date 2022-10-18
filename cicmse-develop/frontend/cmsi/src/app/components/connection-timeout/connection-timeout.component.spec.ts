import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionTimeoutComponent } from './connection-timeout.component';

describe('ConnectionTimeoutComponent', () => {
  let component: ConnectionTimeoutComponent;
  let fixture: ComponentFixture<ConnectionTimeoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConnectionTimeoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConnectionTimeoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
