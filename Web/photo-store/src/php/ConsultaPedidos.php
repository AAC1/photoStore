<?PHP

//header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Allow: GET, POST, OPTIONS, PUT, DELETE");
//header('Access-Control-Allow-Credentials', 'true');

define('DOC_ROOT_PATH', $_SERVER['DOCUMENT_ROOT'].'/photo-store/');
include  DOC_ROOT_PATH.'/src/php/util/generalMethods.php';
require DOC_ROOT_PATH.'/src/php/util/Connection.php';
// Get the posted data.
$postdata = file_get_contents("php://input");

if(isset($postdata) && !empty($postdata))
{
        try {
        // Extract the data Request
        $request = json_decode($postdata);

        $pedidos = [];
        $sql = "SELECT * FROM store_pedido ";
        
        $sql = $sql." WHERE folio like '%";
        
        if(property_exists($request,'folio') && trim($request->folio) != '' ) {
                $sql = $sql.trim($request->folio)."%";
        }
        $sql = $sql."' ";
        if(property_exists($request,'cliente') && trim($request->cliente) != '' ) {
                $sql = $sql." AND cliente like '%";
                $sql = $sql.trim($request->cliente)."%'";
        }
        if(property_exists($request,'estatus') && trim($request->estatus) != '' ) {
                $sql = $sql." AND esatus = '";
                $sql = $sql.trim($request->estatus)."' ";
        }
        if(property_exists($request,'fec_pedidoIni') && trim($request->fec_pedidoIni) != '' ) {
                $sql = $sql." AND fec_pedido >= '";
                $sql = $sql.trim($request->fec_pedidoIni)."' ";
        }
        if(property_exists($request,'fec_pedidoFin') && trim($request->fec_pedidoFin) != '' ) {
                $sql = $sql." AND fec_pedido <= '";
                $sql = $sql.trim($request->fec_pedidoFin)."' ";
        }
//        echo $sql;

        $conn = getConnection();
        if($conn == null)echo '<p>No fue posible obtener datos. Error de conexi\u00F3n</p>';
        $result = $conn->query($sql);
        
        if ($result->num_rows > 0) {
                $idx=0;
                while($row = $result->fetch_assoc()) {
                $pedidos[$idx]['id_pedido'] = $row['id_pedido'];
                $pedidos[$idx]['folio'] = $row['folio'];
                $pedidos[$idx]['cliente'] = $row['cliente'];
                $pedidos[$idx]['telefono'] = $row['telefono'];
                $pedidos[$idx]['descripcion'] = $row['descripcion'];
                $pedidos[$idx]['fec_pedido'] = $row['fec_pedido'];
                $pedidos[$idx]['fec_entregado'] = $row['fec_entregado'];
                $pedidos[$idx]['monto_ant'] = $row['monto_ant'];
                $pedidos[$idx]['monto_total'] = $row['monto_total'];
                $pedidos[$idx]['id_estatus'] = $row['id_estatus'];
                $idx++;
                }
                header('Content-Type: application/json');
                echo json_encode(['data'=>$pedidos]);//($pedidos);//(['data'=>$pedidos]);
        } else {
                http_response_code(404);
                echo "No hay datos";
        }
        $conn->close();
} catch (Exception $e) {
        header("HTTP/1.1 500 Internal Server Error");
        echo '{"data": "Exception occurred: '.$e->getMessage().'"}';
    }
}
?>