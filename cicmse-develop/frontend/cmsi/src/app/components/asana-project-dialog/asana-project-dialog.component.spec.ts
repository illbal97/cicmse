import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsanaProjectDialogComponent } from './asana-project-dialog.component';

describe('AsanaProjectDialogComponent', () => {
  let component: AsanaProjectDialogComponent;
  let fixture: ComponentFixture<AsanaProjectDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsanaProjectDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsanaProjectDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
