import config from 'src/app/config/global.json';
import { Router } from '@angular/router';

export class GlobalComponent {
    private router: Router ;
    constructor(_router: Router){
        this.router = _router;
    }
    isTimeOutOver =():boolean=>{
        var usrLogged = JSON.parse(sessionStorage.getItem('usrLogged'));
        var resp = false;
      //  console.log("dateTima: "+usrLogged.dateTime)
        var dateTime =  new Date().getTime() -usrLogged.dateTime;
     //   console.log(dateTime);

        if(dateTime > config.session.timeout) resp = true;
        else{
            usrLogged.dateTime = new Date().getTime();
            sessionStorage.setItem('usrLogged',JSON.stringify(usrLogged));
        }
        return resp;
    }

    logout=()=>{
        sessionStorage.removeItem('usrLogged');
        this.router.navigate(['/']);
        setTimeout(()=> { window.location.reload(); }, 100);
        
      }

}