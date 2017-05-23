<?php


 

$dbc = mysqli_connect( "46.101.171.158:80", "m.orazaly","CXZ4O0D", "madiyar_orazaly")  or die('Connection error!');
 
$result = mysqli_query($dbc, "select PlayerID, Major from Players_In_Squad s, Users u Where UserName='login_session' AND s.SquadID=u.SquadID");
$id = $_GET['id'];
$id1=$id%1000;
$id2=($id-$id1)/1000;
$major='SUB';
if(mysqli_num_rows($result)<5){
	$major='MAI';
}else{
	$major='SUB';
}
$query = "insert into Players_In_Squad(SquadID, PlayerID, Major) values ($id2, $id1, '$major')";
mysqli_query($dbc, $query) or die('Database error!');
if(mysqli_num_rows($result)==8){
	header('location:home.php');
}else{
	header('location:firsttime.php');
}
?>