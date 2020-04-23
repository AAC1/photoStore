
//var http = require("http");
var express = require('express')
var app = express();

//setExpress =()=>{
    app.post('/reporte',(req,res)=>{
    console.log(res);

    })

    //app.use(serveStatic(__dirname + "/dist"));

    //var path = require('path');
   // var serveStatic = require('serve-static');


    var port = process.env.PORT || 3000;
    var hostname = '127.0.0.1';

    app.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
    });

    //var server = http.createServer(app);
    //server.listen(3000);
//}
//module.exports.setExpress = this.setExpress();
