import { Component, OnInit } from '@angular/core';
import { UsuarioService } from 'src/app/services/usuario.service';

import { UserSession } from 'src/app/objects/UserSession';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private usuarioService: UsuarioService) { }
  ngOnInit() {
  }

  json = { login:'',passwd:'' }
  
  
  loginService = async()=>{

    console.log("request:");
    console.log(this.json);
    return (await this.usuarioService.login(this.json)).subscribe(
      (res:UserSession) => {

        console.log("res:");
        console.log(res);
        //if(res && res.response){
        //}
      },
      (err) => {
        console.log(err)
      //  this.error = err;
      }
    );
  }


}
