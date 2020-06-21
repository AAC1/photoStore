import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { GlobalComponent } from '../global/global.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent extends GlobalComponent implements OnInit {

  constructor(_router:Router) {
    super(_router);
  }
  sessionUser = '';
  ngOnInit() {
    let strObj = sessionStorage.getItem('usrLogged')
    
    
    if(strObj !=null && strObj!==undefined && strObj.length>0){
      console.log(strObj);
      let sessionObj = JSON.parse(strObj);
      this.sessionUser = sessionObj.nombre;
    }
    
  }
  
}
