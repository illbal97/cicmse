import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AwsHomeComponent } from './aws-home.component';

describe('AwsHomeComponent', () => {
  let component: AwsHomeComponent;
  let fixture: ComponentFixture<AwsHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AwsHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AwsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
