<?php
include('login.php'); // Includes Login Script

if(isset($_SESSION['login_user'])){
	header("location: home.php");
}
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet">
	<link href="css\login.css" rel="stylesheet" type="text/css">
    <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<div id="main">
    <div class="container">
      <div class="row">
        <h2 class="text-center">Welcome to Our Fantasy Football Website!</h2>
      </div>
    </div>
	<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link href="http://fonts.googleapis.com/css?family=Varela+Round" rel="stylesheet" type="text/css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.13.1/jquery.validate.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<div class="text-center" style="padding:50px 0">
      <div class="logo">login</div>
	  <div class="login-form-1">
		<div id="login">
		<form action="" method="post" id="login-form" class="text-left">
		<div class="main-login-form">
		<div class="login-group">
		<div class="form-group">
		<label for="username" class="sr-only">UserName :</label>
		<input id="name" name="username" placeholder="username" type="text" class="form-control">
		</div>
		<div class="form-group">
		<label for="password" class="sr-only">Password :</label>
		<input id="password" name="password" placeholder="**********" type="password" class="form-control">
		</div>
		</div>
		<button name="submit" type="submit" value=" Login " class="login-button">
              <i class="fa fa-chevron-right"></i>
            </button>
		<span><?php echo $error; ?></span>
		</div>
		<div class="etc-login-form">
            <p>new user?
              <a href="register.php">create new account</a>
            </p>
          </div>
		</form>
		</div>
		</div>
		</div>
</body>
</html>