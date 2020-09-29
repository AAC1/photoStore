<?PHP
class Sucursal{
    private $id_sucursal;
    private $sucursal;
    private $razon_social;
    private $direccion;
    private $telefono;
    private $prefijo;
    
    public function getId_sucursal(){
        return $this->id_sucursal;
    }
    public function setId_sucursal($id_sucursal){
        $this->id_sucursal=$id_sucursal;
    }
    
    public function getSucursal(){
        return $this->sucursal;
    }
    public function setSucursal($sucursal){
        $this->sucursal=$sucursal;
    }

    public function getRazon_social(){
        return $this->razon_social;
    }
    public function setRazon_social($code){
        $this->razon_social=$razon_social;
    }

    public function getDireccion(){
        return $this->direccion;
    }
    public function setDireccion($direccion){
        $this->direccion=$direccion;
    }

    public function getTelefono(){
        return $this->telefono;
    }
    public function setTelefono($telefono){
        $this->telefono=$telefono;
    }

    public function get(){
        return $this->prefijo;
    }
    public function set($prefijo){
        $this->prefijo=$prefijo;
    }

}
?>