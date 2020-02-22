<?php 
header('Access-Control-Allow-Origin: *'); 
header("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept");

define('DOC_ROOT_PATH', $_SERVER['DOCUMENT_ROOT'].'/photo-store/');
include  DOC_ROOT_PATH.'/src/php/util/generalMethods.php';?>
<?php
function getListProduct(){
    $conn = getConnection("root","admin123","localhost:3306","macrofoto");
    $sql = "SELECT id_prod,id_padre_prod,producto,estatus FROM store_cat_prod";
    if($conn == null)echo '<p>No fue posible obtener datos. Error de conexi\u00F3n</p>';
    $result = $conn->query($sql);
    
    if ($result->num_rows > 0) {
    // output data of each row
        while($row = $result->fetch_assoc()) {
            echo "id: " . $row["id_prod"]. " - producto: " . $row["producto"]. "<br>";
        }
    } else {
        echo "0 results";
    }
    $conn->close();
};


echo '
<article >
<h1> Sección de Venta</h1>
</article>
';
getListProduct();
?>