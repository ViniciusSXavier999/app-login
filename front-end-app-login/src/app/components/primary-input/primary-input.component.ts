import { CommonModule } from '@angular/common';
import { Component, Input, forwardRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

type InputTypes = "text" | "email" | "password"

@Component({
  selector: 'app-primary-input',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PrimaryInputComponent),
      multi: true

    }
  ],
  templateUrl: './primary-input.component.html',
  styleUrls: ['./primary-input.component.css']
})
export class PrimaryInputComponent implements ControlValueAccessor {
  
  @Input() type: InputTypes = "text";
  @Input() placeholder: string = "";
  @Input() label: string = "";
  @Input() inputName: string = "";

  value: string = ''
  onChance: any = () => {}
  onTouched: any = () => {}

  /*Quando o input receber o valor passado pelo usuario, ele vai receber um event*/ 
  onInput(event: Event) {

    // vamos salver esse evento e salvar como HTML para dizer que é um evento de um input
    const value = (event.target as HTMLInputElement).value
    this.onChance(value)
  }

  writeValue(value: any): void {
      this.value = value
  }

  registerOnChange(fn: any): void {
      this.onChance = fn
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn
  }

  setDisabledState(isDisabled: boolean): void {
      
  }
}
