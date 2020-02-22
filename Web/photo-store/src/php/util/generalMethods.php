<?php

function getConnection($username,$password,$serverName,$database){
    $conn = new mysqli($serverName, $username, $password,$database);

    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
        return null;
    }
    return $conn;
}


?>