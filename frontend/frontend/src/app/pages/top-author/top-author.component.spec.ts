import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopAuthorComponent } from './top-author.component';

describe('TopAuthorComponent', () => {
  let component: TopAuthorComponent;
  let fixture: ComponentFixture<TopAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopAuthorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
