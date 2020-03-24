import {AbstractPage} from '../abstract.po';
import {browser} from 'protractor';

export class SchedulersConfiguration extends AbstractPage {

  openSchedulerConfigurationTab() {
    browser.waitForAngular();
    this.clickElementById('mat-tab-label-0-1');
  }

  clickStartProcessButton(schedulerProcessKey: string) {
    browser.waitForAngular();
    this.clickElementById(`start-scheduler-process-${schedulerProcessKey}`);
  }

  clickEditSchedulerHourButton(schedulerProcessKey: string) {
    browser.waitForAngular();
    this.clickElementById(`edit-scheduler-hour-${schedulerProcessKey}-button`);
  }

  waitForFinishedCreatingIssueScheduler() {
    this.sleep(3000);
  }
}
