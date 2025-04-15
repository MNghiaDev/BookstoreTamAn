import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PickedByAuthorComponent } from './picked-by-author.component';

describe('PickedByAuthorComponent', () => {
  let component: PickedByAuthorComponent;
  let fixture: ComponentFixture<PickedByAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PickedByAuthorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PickedByAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
