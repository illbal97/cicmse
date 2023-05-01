import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddingAsanaPersonalAccessTokenComponent } from './adding-asana-personal-access-token.component';

describe('AddingAsanaPersonalAccessTokenComponent', () => {
  let component: AddingAsanaPersonalAccessTokenComponent;
  let fixture: ComponentFixture<AddingAsanaPersonalAccessTokenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddingAsanaPersonalAccessTokenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddingAsanaPersonalAccessTokenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
