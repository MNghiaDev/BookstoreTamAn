import { Component } from '@angular/core';
import { ToastService } from '../../services/toast.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-toast',
  imports: [NgIf],
  templateUrl: './toast.component.html',
  styleUrl: './toast.component.css'
})
export class ToastComponent {
  message: string = '';
  show: boolean = false;

  constructor(private toastService: ToastService) {}

  ngOnInit(): void {
    this.toastService.toastState.subscribe(message => {
      this.message = message;
      this.show = true;
      setTimeout(() => {
        this.show = false;
      }, 3000); // 3 giây tự tắt
    });
  }
}
