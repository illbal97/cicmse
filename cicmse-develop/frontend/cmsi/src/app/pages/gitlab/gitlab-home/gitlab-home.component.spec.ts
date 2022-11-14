import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GitlabHomeComponent } from './gitlab-home.component';

describe('GitlabHomeComponent', () => {
  let component: GitlabHomeComponent;
  let fixture: ComponentFixture<GitlabHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GitlabHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GitlabHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
