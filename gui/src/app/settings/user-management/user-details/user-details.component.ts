import {Component} from '@angular/core';
import {UserManagementService} from "../user-management.service";

@Component({
  selector: 'app-user-details-management',
  standalone: true,
  imports: [],
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.scss'
})
export class UserDetailsComponent {

  constructor(private svc: UserManagementService) {
  }

}
