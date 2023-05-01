import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AwsS3RdsCreationComponent } from './aws-s3-rds-creation.component';

describe('S3RdsCreationDialogComponent', () => {
  let component: AwsS3RdsCreationComponent;
  let fixture: ComponentFixture<AwsS3RdsCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AwsS3RdsCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AwsS3RdsCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
