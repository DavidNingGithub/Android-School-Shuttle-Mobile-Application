<?php
function getDB() {
   $dbhost="localhost";
   $dbuser="root";
   $dbpass="";
   $dbname="TaoNing";


   // Create a DB connection
   $conn = new mysqli($dbhost, $dbuser, $dbpass, $dbname);
   if ($conn->connect_error) {
         die("Connection failed: " . $conn->connect_error . "\n");
   }

   return $conn;
}
?>


