<?php
include('session.php');
?>
<html><head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
            <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
            <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
            <link href="css\home.css" rel="stylesheet" type="text/css">
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
                            <li class="active button">
                                <a href="#">Home</a>
                            </li>
                            <li>
                                <a href="transfers.php">Transfers</a>
                            </li>
                        <li><a href="logout.php">Logout</a></li></ul>
                    </div>
                </div>
            </div>
            <div class="section">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <?php $connection=mysqli_connect( "46.101.171.158:80", "m.orazaly","CXZ4O0D", "madiyar_orazaly"); 
							if (!$connection) { 
							die( 'Could not connect: ' . mysqli_error()); }
							$result=mysqli_query($connection, "select sum(p.Points) from Player p, Players_In_Squad s, Users u Where u.UserName='$login_session' AND s.SquadID=u.SquadID AND s.Major='MAI' AND p.PlayerID=s.PlayerID");
							while($row=mysqli_fetch_array($result)) {
							echo "<h1 class='text-center text-success field'>Your Points This Week:". $row[ 'sum(p.Points)'] . "</h1>"; }
							mysqli_close($connection); 
							?>
                        </div>
                    </div>
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
								<tr>
								  <th>Team Name</th>
								  <th></th>
								</tr>
							  </thead>
							  <tbody>
								<?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
													if (!$connection) { 
													die('Could not connect: ' . mysqli_error()); 
													}  
													$result = mysqli_query($connection, "select Code, ClubName from Club");
													while($row = mysqli_fetch_array($result)) { 
													echo "<tr>";
													echo "<td>" . $row['ClubName'] . "</td>";
													echo "<td><button type='button' class='btn btn-primary' data-toggle='modal' data-target='#myModal" . $row['Code'] . "'>Choose</button></td>";
													echo "</tr>";} 
													mysqli_close($connection);
													?>
							  </tbody>
							</table>
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
									echo '<form action="out.php" method="post">';  
									$result = mysqli_query($connection, "select p.PlayersName, p.Club_Code, p.Position, p.Points, p.PlayerID, u.SquadID from Player p, Players_In_Squad s, Users u where u.UserName='$login_session' AND s.SquadID=u.SquadId AND p.PlayerID=s.PlayerID AND s.Major='MAI'");
									while($row = mysqli_fetch_array($result)) { 
									$id1 = $row['PlayerID'];
									$sqid=$row['SquadID'];
									$id=$sqid*1000+$id1;
									echo "<tr>"; 
									echo "<td><div class='col-md-12'><a class='btn btn-danger' href='out.php?id=$id'>OUT</a></div></td>"; 
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
									$result = mysqli_query($connection,"select p.PlayersName, p.Club_Code, p.Position, p.Points, p.PlayerID, u.SquadID from Player p, Players_In_Squad s, Users u where u.UserName='$login_session' AND s.SquadID=u.SquadId AND p.PlayerID=s.PlayerID AND s.Major='SUB'");
									$dis='';
									if(mysqli_num_rows($result)<4){
										$dis='disabled';
									}
									
									while($row = mysqli_fetch_array($result)) { 
										$id1 = $row['PlayerID'];
										$sqid=$row['SquadID'];
										$id=$sqid*1000+$id1;
										echo "<tr>"; 
										echo "<td><div class='col-md-12'><a class='btn btn-success $dis' href='in.php?id=$id'>IN</a></div></td>"; 
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
                        <div class="col-md-4">
                            <h1 class="field">General League</h1>
                            <table class="table" id="acrylic">
                                <thead>
                                    <tr>
                                        <th class="success">#</th>
                                        <th class="success">Team Name</th>
                                        <th class="success">User Name</th>
                                        <th class="success">Points</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
									if (!$connection) { 
									die('Could not connect: ' . mysqli_error()); 
									}
									$result = mysqli_query($connection, "select UserName, SquadName, Overall from Squad s, Users u  Where s.SquadID=u.SquadID Order by Overall DESC");
									$i=1;
									while($row = mysqli_fetch_array($result)) { 
									echo "<tr>"; 
									echo "<td>" . $i . "</td>"; 
									echo "<td>" . $row['SquadName'] . "</td>"; 
									echo "<td>" . $row['UserName'] . "</td>";
									echo "<td>" . $row['Overall'] . "</td>"; 
									echo "</tr>"; 
									$i++;} 
									mysqli_close($connection);
									?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
			<?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
									if (!$connection) { 
									die('Could not connect: ' . mysqli_error()); 
									}  
									$result = mysqli_query($connection, "select Code, ClubName from Club");
									while($row = mysqli_fetch_array($result)) {
										$x=$row['Code'];
										echo "<div class='modal fade' id='myModal" . $x . "' role='dialog'>";
										echo "<div class='modal-dialog'>
												<!-- Modal conctent-->
												<div class='modal-content'>
												  <div class='modal-header'>
													<button type='button' class='close' data-dismiss='modal'>×</button>
													<h4 class='modal-title'>Choose player</h4>
												  </div>
												  <div class='modal-body'>
													<table class='table'>
													  <thead>
														<tr class='info'>
														  <th></th>
														  <th>Player</th>
														  <th>Position</th>
														  <th>Availability</th>
														  <th>Total Points</th>
														</tr>
													  </thead>
													  <tbody>";
										$result2 = mysqli_query($connection, "Select PlayersName, Position, Availability, Points, PlayerID, SquadID From Player, Users where Club_Code='$x' AND UserName='$login_session'");		  
										while($row = mysqli_fetch_array($result2)){
											$dis='';
											$id1 = $row['PlayerID'];
											$id2 = $row['SquadID'];
											$id=$id2*1000+$id1;
											echo "<tr>"; 
											echo "<td><div class='col-md-12'><a class='btn btn-success $dis' href='addplayer.php?id=$id'>✓</a></div></td>"; 
											echo "<td>" . $row['PlayersName'] . "</td>"; 
											echo "<td>" . $row['Position'] . "</td>";
											echo "<td>" . $row['Availability'] . "</td>";
											echo "<td>" . $row['Points'] . "</td>"; 
											echo "</tr>";
										}
										echo "</tbody>
											</table>
										  </div>
										  <div class='modal-footer'>
											<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>
										  </div>
										</div>
									  </div>
									</div>";
									}
									?>
    </body></html>