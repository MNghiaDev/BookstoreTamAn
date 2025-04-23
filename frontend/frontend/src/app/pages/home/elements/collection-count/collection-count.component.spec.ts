import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionCountComponent } from './collection-count.component';

describe('CollectionCountComponent', () => {
  let component: CollectionCountComponent;
  let fixture: ComponentFixture<CollectionCountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionCountComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionCountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
