<?php
$connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
if (!$connection) { 
die('Could not connect: ' . mysqli_error()); 
} 
$result = mysqli_query($connection, "select PlayersName, Club_Code, Position, Points, PlayerID From Player");
while($row = mysqli_fetch_array($result)) {
$x=$row['PlayerID'];
$id = $_GET['id110'];	
$query = "update Player set points=points+$id where playersName='Hazard'";
mysqli_query($connection, $query) or die('Database error!');
}
header('location:admin.php');
?>