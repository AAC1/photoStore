<?php

define('DB_HOST', 'localhost:3306');
define('DB_USER', 'root');
define('DB_PASS', 'admin123');
define('DB_NAME', 'macrofoto');
function getConnection(){
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS,DB_NAME);

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
        return null;
    }
    return $conn;
}


?>