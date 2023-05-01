import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefreshTokenInvalidComponent } from './refresh-token-invalid.component';

describe('RefreshTokenInvalidComponent', () => {
  let component: RefreshTokenInvalidComponent;
  let fixture: ComponentFixture<RefreshTokenInvalidComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefreshTokenInvalidComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshTokenInvalidComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
