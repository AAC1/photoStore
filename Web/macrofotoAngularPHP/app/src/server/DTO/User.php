<?PHP

class User{
    private $id_usr;
    private $login;
    private $nombre;
    private $correo;
    private $telefono;
    private $direccion;
    private $intentos;
    private $bloqueado;
    private $activo;
    private $id_perfil;
    private $id_sucursal;
    private $sucursalUsr;
    private $response;

    public function getId_usr(){
        return $this->id_usr;
    }
    public function setId_usr($id_usr){
        $this->id_usr=$id_usr;
    }

    public function getLogin(){
        return $this->login;
    }
    public function setLogin($login){
        $this->login=$login;
    }

    public function getNombre(){
        return $this->nombre;
    }
    public function setNombre($nombre){
        $this->nombre=$nombre;
    }

    public function getCorreo(){
        return $this->correo;
    }
    public function setCorreo($correo){
        $this->correo=$correo;
    }

    public function getTelefono(){
        return $this->telefono;
    }
    public function setTelefono($telefono){
        $this->telefono=$telefono;
    }

    public function getDireccion(){
        return $this->direccion;
    }
    public function setDireccion($direccion){
        $this->direccion=$direccion;
    }

    public function getIntentos(){
        return $this->intentos;
    }
    public function setIntentos($intentos){
        $this->intentos=$intentos;
    }

    public function getBloqueado(){
        return $this->bloqueado;
    }
    public function setBloqueado($bloqueado){
        $this->bloqueado=$bloqueado;
    }

    public function getActivo(){
        return $this->activo;
    }
    public function setActivo($activo){
        $this->activo=$activo;
    }

    public function getId_perfil(){
        return $this->id_perfil;
    }
    public function setId_perfil($id_perfil){
        $this->id_perfil=$id_perfil;
    }

    public function getId_sucursal(){
        return $this->id_sucursal;
    }
    public function setId_sucursal($id_sucursal){
        $this->id_sucursal=$id_sucursal;
    }

    public function getSucursalUsr(){
        return $this->sucursalUsr;
    }
    public function setSucursalUsr($sucursalUsr){
        $this->sucursalUsr=$sucursalUsr;
    }

    public function getResponse(){
        return $this->response;
    }
    public function setResponse($response){
        $this->response=$response;
    }

}
?>