import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AwsAccessKeysAddingComponent } from './aws-access-keys-adding.component';

describe('AwsAccessKeysAddingComponent', () => {
  let component: AwsAccessKeysAddingComponent;
  let fixture: ComponentFixture<AwsAccessKeysAddingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AwsAccessKeysAddingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AwsAccessKeysAddingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
