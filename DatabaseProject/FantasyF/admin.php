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
								echo "<li class='active button'><a href='#'>Admin Page</a></li>";
							}
							?>
                            <li>
                                <a href="home.php">Home</a>
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
                                    <tr class="info">
                                        <th>PlayersName</th>
                                        <th>Club_Code</th>
                                        <th>Position</th>
                                        <th>Points</th>
										<th>Input Points</th>
                                    </tr>
                                </thead>
                                <tbody>
                                 <?php $connection = mysqli_connect("46.101.171.158:80", "m.orazaly", "CXZ4O0D", "madiyar_orazaly"); 
									if (!$connection) { 
									die('Could not connect: ' . mysqli_error()); 
									} 
									$result = mysqli_query($connection, "select PlayersName, Club_Code, Position, Points, PlayerID From Player");
									while($row = mysqli_fetch_array($result)) {
									$id=$row['PlayerID'];
									echo "<tr>"; 
									echo "<td>" . $row['PlayersName'] . "</td>"; 
									echo "<td>" . $row['Club_Code'] . "</td>";
									echo "<td>" . $row['Position'] . "</td>";
									echo "<td>" . $row['Points'] . "</td>"; 
									echo "<td>  <form action='admin.php'>
										  <select name='addpoint'>
											<option value='0'>0</option>
											<option value='1'>1</option>
											<option value='2'>2</option>
											<option value='3'>3</option>
											<option value='4'>4</option>
											<option value='5'>5</option>
											<option value='6'>6</option>
											<option value='7'>7</option>
											<option value='8'>8</option>
											<option value='9'>9</option>
											</select>";
									echo "</tr>"; 
									} 
									mysqli_close($connection);
									?>
                                </tbody>
                            </table>
							<input type='submit'><td>
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
    </body></html>