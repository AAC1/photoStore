import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(private router: Router) { }
  sessionUser = '';
  ngOnInit() {
    let strObj = sessionStorage.getItem('usrLogged')
    
    
    if(strObj !=null && strObj!==undefined && strObj.length>0){
      console.log(strObj);
      let sessionObj = JSON.parse(strObj);
      this.sessionUser = sessionObj.nombre;
    }
    
  }
  logout=()=>{
    sessionStorage.removeItem('usrLogged');
    this.router.navigate(['/']);
    window.location.reload();
  }
}
