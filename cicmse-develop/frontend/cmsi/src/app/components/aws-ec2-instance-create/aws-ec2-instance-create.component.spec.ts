import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AwsEc2InstanceCreateComponent } from './aws-ec2-instance-create.component';

describe('AwsEc2InstanceCreateComponent', () => {
  let component: AwsEc2InstanceCreateComponent;
  let fixture: ComponentFixture<AwsEc2InstanceCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AwsEc2InstanceCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AwsEc2InstanceCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
