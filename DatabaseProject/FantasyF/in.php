<?php
 
$id = $_GET['id'];
 
 $dbc = mysqli_connect( "46.101.171.158:80", "m.orazaly","CXZ4O0D", "madiyar_orazaly")  or die('Connection error!');
 
 
 
$query = "update Players_In_Squad set Major='MAI' WHERE PlayerID = $id%1000 AND SquadID=($id-$id%1000)/1000";
mysqli_query($dbc, $query) or die('Database error!');
header('location:home.php');
?>