import { Component,ViewChild } from '@angular/core';
import * as _angular_ from 'angular';

declare global {
  var angular: typeof _angular_;
  
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ticketApp';
  
  @ViewChild('sidebar') sidebar;
  @ViewChild('content') divContent;

  toggleBar(): void{
    console.log(this.sidebar);
    console.log(this.divContent);
    
    this.sidebar.nativeElement.classList.toggle('active');
  //  this.divContent.nativeElement.classList.toggle('active');

  }
}
