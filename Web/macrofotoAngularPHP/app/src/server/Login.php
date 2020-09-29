<?PHP
header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Allow: GET, POST, OPTIONS, PUT, DELETE");

define('DOC_ROOT_PATH', $_SERVER['DOCUMENT_ROOT'] . '/server/');
include DOC_ROOT_PATH . '/src/php/util/generalMethods.php';
require DOC_ROOT_PATH . '/src/php/util/Connection.php';
require DOC_ROOT_PATH . '/src/php/util/Constants.php';
require DOC_ROOT_PATH . '/src/php/DTO/User.php';
require DOC_ROOT_PATH . '/src/php/DTO/Response.php';
require DOC_ROOT_PATH . '/src/php/DTO/Sucursal.php';

$postdata = file_get_contents("php://input");

class UserSession
{
    function getSucById($id_sucursal){
        $sucursalUsr = new Sucursal;
        try {
            $usr = [];
            $sql = "SELECT * FROM store_sucursal ";

            $sql = $sql . " WHERE id_sucursal = " . $id_sucursal . "";

            //        echo $sql;

            $connSuc = getConnection();
            if ($connSuc == null) {
                return $sucursalUsr; 
            }

            $result = $connSuc->query($sql);
            
            if ($result->num_rows > 0) {
                $idx = 0;
                while ($row = $result->fetch_assoc()) {
                    $sucursalUsr->id_sucursal = $row['id_sucursal'];
                    $sucursalUsr->sucursal = $row['sucursal'];
                    $sucursalUsr->razon_social = $row['razon_social'];
                    $sucursalUsr->direccion = $row['direccion'];
                    $sucursalUsr->telefono = $row['telefono'];
                    $sucursalUsr->bloqueado = $row['bloqueado'];
                    $sucursalUsr->prefijo = $row['prefijo'];
                    $idx++;
                }
            } 
            if($connSuc != null)$connSuc->close();
        } catch (Exception $e) {
            if($connSuc != null)$connSuc->close();
        }
        return $sucursalUsr;//son_encode($sucursalUsr);
    }
    function getUserSession($user, $password)
    {

        $usrSessionObj = new User;
        $usrSessionObj->response = new Response;
        try {
            
            $usr = [];
            $sql = "SELECT * FROM store_usuario ";

            $sql = $sql . " WHERE login = '" . $user . "'";
            $sql = $sql . " AND passwd = '" . $password . "'";

            //        echo $sql;

            $conn = getConnection();
            if ($conn == null) {
         //       header("HTTP/1.1 500 Internal Server Error");
                $usrSessionObj->response->message=  'No fue posible obtener datos. Error de conexi\u00F3n';
                $usrSessionObj->response->status= "ERROR";
                $usrSessionObj->response->code= -1;
                echo json_encode(['data' => $usrSessionObj]);
                return;
            }

            $result = $conn->query($sql);

            if ($result->num_rows > 0) {
                $idx = 0;
                while ($row = $result->fetch_assoc()) {
                    $usrSessionObj->id_usr = $row['id_usr'];
                    $usrSessionObj->login = $row['login'];
                    $usrSessionObj->nombre = $row['nombre'];
                    $usrSessionObj->correo = $row['correo'];
                    $usrSessionObj->telefono = $row['telefono'];
                    $usrSessionObj->direccion = $row['direccion'];
                    $usrSessionObj->intentos = $row['intentos'];
                    $usrSessionObj->bloqueado = $row['bloqueado'];
                    $usrSessionObj->activo = $row['activo'];
                    $usrSessionObj->id_perfil = $row['id_perfil'];
                    $idx++;
                }

                

                if($usrSessionObj->bloqueado == "1"){
                    $usrSessionObj->response->message= "Usuario bloqueado. Contacte al administrador";
                    $usrSessionObj->response->status= "ERROR";
                    $usrSessionObj->response->code= -1;
                    echo json_encode(['data' => $usrSessionObj]);
                    return;
                }
                if($usrSessionObj->bloquactivoeado == "0"){
                    $usrSessionObj->response->message= "Usuario dado de baja. Contacte al administrador";
                    $usrSessionObj->response->status= "ERROR";
                    $usrSessionObj->response->code= -1;
                    echo json_encode(['data' => $usrSessionObj]);
                    return;
                }
                $usrSessionObj->sucursalUsr = getSucById($usrSessionObj->id_usr);
                $usrSessionObj->response->message= "";
                $usrSessionObj->response->status= "OK";
                $usrSessionObj->response->code= 0;
                
                echo json_encode(['data' => $usrSessionObj]); 
            } else {
                $usrSessionObj->response->message= "Usuario no encontrado, valide la contrase\u00F1a y el usuario";
                $usrSessionObj->response->status= "ERROR";
                $usrSessionObj->response->code= -1;
                echo json_encode(['data' => $usrSessionObj]);
                
              //  http_response_code(404);
              //  echo "No hay datos";
            }
            $conn->close();
        } catch (Exception $e) {
            
          //  header("HTTP/1.1 500 Internal Server Error");
            $usrSessionObj->response->message= $e->getMessage();
            $usrSessionObj->response->status= "ERROR";
            $usrSessionObj->response->code= -1;

            echo json_encode(['data' => $usrSessionObj]);
            if($conn != null)$conn->close();

          //  echo '{"data": "Exception occurred: ' . $e->getMessage() . '"} ' ;
        }
    }

}

$usrObj = new UserSession;
if (isset($postdata) && !empty($postdata)) {
    $usrSessionObj = new User;
    $usrSessionObj->response = new Response;
    try{
        header('Content-Type: application/json');
        $request = json_decode($postdata);

        $passwdIni = trim($request->passwd) . $SALT;
        $passwd = hash('sha256', $passwdIni);
            
        $usrObj->getUserSession(trim($request->login), $passwd);

    } 
    catch (Exception $e) {
       // header("HTTP/1.1 500 Internal Server Error");
            $usrSessionObj->response->message= $e->getMessage();
            $usrSessionObj->response->status= "ERROR";
            $usrSessionObj->response->code= -1;

            echo json_encode(['data' => $usrSessionObj]);
    }
    
}

?>