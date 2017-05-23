<?php
include('session.php');
?>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="css\Transfers.css" rel="stylesheet" type="text/css">
  </head><body data-spy="scroll">
    <div class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand"><span>Fantasy Football</span></a>
        </div>
        <div class="collapse navbar-collapse" id="navbar-ex-collapse">
          <ul class="nav navbar-nav navbar-right">
			<?php
							$admin_check=$_SESSION['login_user'];
							if($admin_check=='Admin'){
								echo "<li><a href='Admin.php'>Admin Page</a></li>";
							}
							?>
            <li class="">
              <a href="Home.php">Home</a>
            </li>
            <li class="active">
              <a href="#">Transfers</a>
            </li>
			<li><a href="logout.php">Logout</a></li></ul>
          </ul>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="row">
          <div class="col-md-8 field">
            <?php $connection=mysql_connect( "46.101.171.158:80", "m.orazaly","CXZ4O0D"); 
							if (!$connection) { 
							die( 'Could not connect: ' . mysql_error()); }
                            mysql_select_db( "madiyar_orazaly", $connection);
							$result=mysql_query("select s.SquadName from Squad s, Users u where u.UserName='$login_session' AND s.SquadID=u.SquadId"); 
							while($row=mysql_fetch_array($result)) {
							echo "<h1>". $row[ 'SquadName'] . "</h1>"; }
							mysql_close($connection); 
							?>
            <table class="table">
              <thead>
                <tr class="info">
				<th>Main Squad</th>
				<th>PlayersName</th>
                <th>Club_Code</th>
                <th>Position</th>
                <th>Points</th>
                </tr>
              </thead>
              <tbody>
                <?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
									if (!$connection) { 
									die('Could not connect: ' . mysqli_error()); 
									}
									$result = mysqli_query($connection, "select p.PlayersName, p.Club_Code, p.Position, p.Points, p.PlayerID, u.SquadID from Player p, Players_In_Squad s, Users u where u.UserName='$login_session' AND s.SquadID=u.SquadId AND p.PlayerID=s.PlayerID AND s.Major='MAI'");
									while($row = mysqli_fetch_array($result)) { 
									$id1 = $row['PlayerID'];
									$sqid=$row['SquadID'];
									$id=$sqid*1000+$id1;
									echo "<tr>"; 
									echo "<td> <form method='POST' action='transfersout.php'><div class='col-md-12'><a name='submitBtn' type='submit' value='$id' class='btn btn-danger' href='transfersout.php?id=$id'>X</a></div></form></td>"; 
									echo "<td>" . $row['PlayersName'] . "</td>"; 
									echo "<td>" . $row['Club_Code'] . "</td>";
									echo "<td>" . $row['Position'] . "</td>";
									echo "<td>" . $row['Points'] . "</td>"; 
									echo "</tr>"; } 
									mysqli_close($connection);
									?>
                <tr class="info">
                                        <th>Substitutes</th>
										<th>PlayersName</th>
                                        <th>Club_Code</th>
                                        <th>Position</th>
                                        <th>Points</th>
                </tr>
                <?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
									if (!$connection) { 
									die('Could not connect: ' . mysqli_error()); 
									}  
									$result = mysqli_query($connection, "select p.PlayersName, p.Club_Code, p.Position, p.Points, p.PlayerID, u.SquadID from Player p, Players_In_Squad s, Users u where u.UserName='$login_session' AND s.SquadID=u.SquadId AND p.PlayerID=s.PlayerID AND s.Major='SUB'");
									while($row = mysqli_fetch_array($result)) { 
									$id1 = $row['PlayerID'];
									$sqid=$row['SquadID'];
									$id=$sqid*1000+$id1;
									echo "<tr>"; 
									echo "<td><div class='col-md-12'><a name='submitBtn' type='submit' value='$id' class='btn btn-danger' href='transfersout.php?id=$id'>X</a></div></td>"; 
									echo "<td>" . $row['PlayersName'] . "</td>"; 
									echo "<td>" . $row['Club_Code'] . "</td>";
									echo "<td>" . $row['Position'] . "</td>";
									echo "<td>" . $row['Points'] . "</td>"; 
									echo "</tr>"; } 
									mysqli_close($connection);
									?>
              </tbody>
            </table>
          </div>
</body></html>