import * as express from "express";
import {PedidosServer} from "./Controller/pedidos.server";
import { createConnection, Connection } from 'typeorm';
import { UsuarioServer } from './Controller/usuario.server';



export class Start{

    public app = express();
    public port = 3000;
    public connection!: Connection; 
    
    constructor( port: number) {
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

        this.app.get("/", (req: any, res: any) => {
            res.send("Hello world!");
        });
        
        let pedidos = new PedidosServer();
        pedidos.resourcesPedidos(this.app,this.connection);
        
        let usuario= new UsuarioServer();
        usuario.resourcesUsuario(this.app,this.connection);

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