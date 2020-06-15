import * as express from "express";
import {PedidosServer} from "./Controller/pedidos.server";
import { createConnection, Connection } from 'typeorm';
import { UsuarioServer } from './Controller/usuario.server';
import { createProxyMiddleware } from 'http-proxy-middleware';
import { CatStatusServer } from './Controller/catStatus.server';


export class Start{

    public app = express();
    
    public port = 3000;
    public connection!: Connection; 
    public urlOrigin:string ;

    constructor( port: number) {
        this.urlOrigin = "http://localhost:4200";
        this.app = express();
        this.port = port;
        this.initializeModels();
        this.initializeMiddlewares();
        this.initializeControllers();
    }

    private async initializeModels() {
        console.log("Initializing connection...");
        const connection = await createConnection();
        if (connection === undefined) { 
            console.log("Error connecting to database");
            throw new Error('Error connecting to database'); } // In case the connection failed, the app stops.
        connection.synchronize(); // this updates the database schema to match the models' definitions
        this.connection = connection; // Store the connection object in the class instance.
        
    }

    // Here we can add all the global middlewares for our application. (Those that will work across every contoller)
    private initializeMiddlewares() {
        this.app.use(express.json());
    }
    
    private initializeControllers() {

  //      controllers.forEach((controller) => {
   //         this.app.use('/', controller.router);
  //      });
      //  const wsProxy = createProxyMiddleware({ target: this.urlOrigin, changeOrigin: true })
        //this.app.use('/api',wsProxy);
        this.app.use(function(req, res, next) {
            res.header("Access-Control-Allow-Origin", "*");// Request methods you wish to allow
            res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
        
            // Request headers you wish to allow
            res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
        
            // Set to true if you need the website to include cookies in the requests sent
            // to the API (e.g. in case you use sessions)
            res.setHeader('Access-Control-Allow-Credentials', "true");
            next();
            });
        
        this.app.get("/", (req: any, res: any) => {
            res.send("Hello world!");
        });
        
        let pedidos = new PedidosServer();
        pedidos.resourcesPedidos(this.app,this.connection);
        let usuario= new UsuarioServer();
        usuario.resourcesUsuario(this.app,this.connection);
        let catStatus = new CatStatusServer();
        catStatus.resourcesCatStatus(this.app,this.connection);
        
        this.listen();
    }

    private listen() {
        this.app.listen(this.port, () => {
            console.log(`Server running on port ${this.port}`);
        });
    }
    
}

var init =new Start(3000);




/*
module.exports = function(app){
    app.get('/getPedidos', function (req, res) {+
        console.log(req);

        res.send('Hello,  Reporte works!');
    });

}
*/