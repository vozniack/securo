import { NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { ContentComponent } from './core/content/content.component';
import { ToolbarComponent } from './core/toolbar/toolbar.component';

@Component({
  selector: 'sec-root',
  standalone: true,
  imports: [ToolbarComponent, NgIf, ContentComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

  logged: boolean = true;

  toolbar: boolean = true;
  sidebar: boolean = true;
}
