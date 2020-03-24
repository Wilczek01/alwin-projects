import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IssueAuditComponent} from './issue-audit.component';
import {AlwinMatModule} from '../../../../alwin-mat.module';

describe('IssueAuditComponent', () => {
  let component: IssueAuditComponent;
  let fixture: ComponentFixture<IssueAuditComponent>;
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        AlwinMatModule],
      declarations: [
        IssueAuditComponent],
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IssueAuditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
