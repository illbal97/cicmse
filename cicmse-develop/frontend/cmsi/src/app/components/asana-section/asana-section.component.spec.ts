import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AsanaSectionComponent } from './asana-section.component';

describe('AsanaSectionComponent', () => {
  let component: AsanaSectionComponent;
  let fixture: ComponentFixture<AsanaSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AsanaSectionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsanaSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
