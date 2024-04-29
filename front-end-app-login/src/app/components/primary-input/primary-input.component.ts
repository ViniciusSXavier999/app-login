import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

type InputTypes = "text" | "email" | "password"

@Component({
  selector: 'app-primary-input',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './primary-input.component.html',
  styleUrls: ['./primary-input.component.css']
})
export class PrimaryInputComponent {
  @Input() type: InputTypes = "text";
  @Input() formName: string = "";
  @Input() placeholder: string = "";
}
