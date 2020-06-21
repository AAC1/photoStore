import { Component, OnInit,ViewChild } from '@angular/core';
import { UsuarioService } from 'src/app/services/usuario.service';

import { UserSession } from 'src/app/objects/UserSession';
import { Router } from '@angular/router';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private usuarioService: UsuarioService,private router: Router) { }
  ngOnInit() {
  }
  @ViewChild('alertLogin') alertLogin;

  json = { login:'',passwd:'' }
  jsonAlerts ={ message:'', typeMessage:'',showMessage:false}
  loginService = async()=>{
    this.jsonAlerts.showMessage = false;
    console.log("request:");
    console.log(this.json);
    return (await this.usuarioService.login(this.json)).subscribe(
      (res:UserSession) => {
        
        console.log("res:");
        console.log(res);
        if(res !==undefined && res.response !== undefined){
          if(res.response.message.length>0){
            this.jsonAlerts.message=res.response.message;
            this.jsonAlerts.showMessage = true;
          }
          this.jsonAlerts.typeMessage=res.response.status;

          if(res.response.status=="OK"){
            res.dateTime = new Date().getTime();
            sessionStorage.setItem('usrLogged',JSON.stringify(res));
            this.router.navigate(['/home']);
            setTimeout(()=> { window.location.reload(); }, 100);
          }
        }
      },
      (err) => {
        console.log(err)
      //  this.error = err;
      }
    );
  }

dismissAlert = ()=>{
  this.jsonAlerts.showMessage=false
}
  

}
