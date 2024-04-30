import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-default-login-layout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './default-login-layout.component.html',
  styleUrls: ['./default-login-layout.component.css']
})
export class DefaultLoginLayoutComponent {
  // Recebendo através de input
  @Input() title: string = "";
  @Input() primaryBtnText: string = "";
  @Input() secondaryBtnText: string = "";

  @Output("submit") onSubmit = new EventEmitter

  @Output("navigate") onNavigate = new EventEmitter

  submit(){
    this.onSubmit.emit()
  }

  navigate() {
    this.onNavigate.emit()
  }
}
