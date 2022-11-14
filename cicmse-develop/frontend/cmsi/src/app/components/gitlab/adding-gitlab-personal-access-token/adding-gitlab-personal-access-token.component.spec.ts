import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddingGitlabPersonalAccessTokenComponent } from './adding-gitlab-personal-access-token.component';

describe('AddingGitlabPersonalAccessTokenComponent', () => {
  let component: AddingGitlabPersonalAccessTokenComponent;
  let fixture: ComponentFixture<AddingGitlabPersonalAccessTokenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddingGitlabPersonalAccessTokenComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddingGitlabPersonalAccessTokenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
