import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsanaTaskComponent } from './asana-task.component';

describe('AsanaTaskComponent', () => {
  let component: AsanaTaskComponent;
  let fixture: ComponentFixture<AsanaTaskComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsanaTaskComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsanaTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
