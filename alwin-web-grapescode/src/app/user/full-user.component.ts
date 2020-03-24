import {Component, Input} from '@angular/core';
import {FullUser} from './full-user';


@Component({
  selector: 'alwin-full-user',
  styleUrls: ['./full-user.component.css'],
  templateUrl: './full-user.component.html'
})
export class FullUsersComponent {

  @Input()
  user: FullUser;
}
